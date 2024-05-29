package com.artificial.soft.groovy.dsl.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.artificial.soft.groovy.dsl.database.entity.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProducts(List<ProductEntity> products);

    @Query("SELECT * FROM products WHERE category_id = :categoryId")
    LiveData<List<ProductEntity>> getProductsByCategory(int categoryId);

}