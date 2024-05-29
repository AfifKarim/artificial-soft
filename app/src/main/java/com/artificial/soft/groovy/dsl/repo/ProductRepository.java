package com.artificial.soft.groovy.dsl.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.artificial.soft.groovy.dsl.database.DB;
import com.artificial.soft.groovy.dsl.database.dao.ProductDao;
import com.artificial.soft.groovy.dsl.database.entity.ProductEntity;
import com.artificial.soft.groovy.dsl.database.model.ProductResponse;
import com.artificial.soft.groovy.dsl.network.ApiClient;
import com.artificial.soft.groovy.dsl.network.ApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private final ProductDao productDao;
    private final ApiService apiService;
    private final ExecutorService executorService;

    public ProductRepository(Application application) {
        DB db = DB.getInstance(application);
        productDao = db.productDao();
        apiService = ApiClient.getInstance().getApiService();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ProductEntity>> getProductsByCategory(int categoryId, String category_name) {
        MutableLiveData<List<ProductEntity>> productsLiveData = new MutableLiveData<>();
        apiService.getProductsByCategory(categoryId).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.body() != null && response.body().status) {
                    executorService.execute(() -> {
                        List<ProductEntity> products = response.body().data;
                        // Set category ID for each product
                        for (ProductEntity product : products) {
                            product.setCategory_id(categoryId);
                            product.setCategory_name(category_name);
                        }
                        productDao.insertProducts(response.body().data);
                        productsLiveData.postValue(response.body().data);
                    });
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                productsLiveData.postValue(null);
            }
        });
        return productsLiveData;
    }
}