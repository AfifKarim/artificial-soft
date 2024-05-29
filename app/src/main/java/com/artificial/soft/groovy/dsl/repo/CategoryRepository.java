package com.artificial.soft.groovy.dsl.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.artificial.soft.groovy.dsl.database.DB;
import com.artificial.soft.groovy.dsl.database.dao.CategoryDao;
import com.artificial.soft.groovy.dsl.database.entity.CategoryEntity;
import com.artificial.soft.groovy.dsl.database.model.CategoryResponse;
import com.artificial.soft.groovy.dsl.network.ApiClient;
import com.artificial.soft.groovy.dsl.network.ApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private final CategoryDao categoryDao;
    private final ApiService apiService;
    private final ExecutorService executorService;

    public CategoryRepository(Application application) {
        DB db = DB.getInstance(application);
        categoryDao = db.categoryDao();
        apiService = ApiClient.getInstance().getApiService();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        MutableLiveData<List<CategoryEntity>> categoriesLiveData = new MutableLiveData<>();
        apiService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.body() != null && response.body().status) {
                    executorService.execute(() -> {
                        categoryDao.insertCategories(response.body().data);
                        categoriesLiveData.postValue(response.body().data);
                    });
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                categoriesLiveData.postValue(null);
            }
        });
        return categoriesLiveData;
    }
}