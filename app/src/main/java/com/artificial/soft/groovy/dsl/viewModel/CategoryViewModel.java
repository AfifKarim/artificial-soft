package com.artificial.soft.groovy.dsl.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.artificial.soft.groovy.dsl.database.entity.CategoryEntity;
import com.artificial.soft.groovy.dsl.repo.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private final CategoryRepository repository;
    private final LiveData<List<CategoryEntity>> categories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);
        categories = repository.getCategories();
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return categories;
    }
}
