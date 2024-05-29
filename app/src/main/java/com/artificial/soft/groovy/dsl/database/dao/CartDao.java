package com.artificial.soft.groovy.dsl.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.artificial.soft.groovy.dsl.database.entity.CartEntity;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insert(CartEntity cart);

    @Update
    void update(CartEntity cart);

    @Delete
    void delete(CartEntity cart);

    @Query("DELETE FROM cart")
    void deleteAllCarts();

    @Query("SELECT * FROM cart ORDER BY cart_id ASC")
    LiveData<List<CartEntity>> getAllCarts();

    @Query("SELECT * FROM cart WHERE cart_product_id = :productId LIMIT 1")
    LiveData<CartEntity> getCartByProductId(int productId);
}
