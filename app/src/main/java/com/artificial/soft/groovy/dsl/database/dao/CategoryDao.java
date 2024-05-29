package com.artificial.soft.groovy.dsl.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.artificial.soft.groovy.dsl.database.entity.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategories(List<CategoryEntity> categories);

    @Query("SELECT * FROM categories")
    LiveData<List<CategoryEntity>> getAllCategories();
}
