package com.artificial.soft.groovy.dsl.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.artificial.soft.groovy.dsl.database.entity.ProductEntity;
import com.artificial.soft.groovy.dsl.repo.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private final ProductRepository repository;
    private LiveData<List<ProductEntity>> products;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
    }

    public void fetchProductsByCategory(int categoryId, String category_name) {
        products = repository.getProductsByCategory(categoryId, category_name);
    }

    public LiveData<List<ProductEntity>> getProducts() {
        return products;
    }
}