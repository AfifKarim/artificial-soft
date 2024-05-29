package com.artificial.soft.groovy.dsl.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.artificial.soft.groovy.dsl.database.entity.CartEntity;
import com.artificial.soft.groovy.dsl.repo.CartRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private CartRepository repository;
    private LiveData<List<CartEntity>> allCarts;

    public CartViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
        allCarts = repository.getAllCarts();
    }

    public void insert(CartEntity cart) {
        repository.insert(cart);
    }

    public void update(CartEntity cart) {
        repository.update(cart);
    }

    public void delete(CartEntity cart) {
        repository.delete(cart);
    }

    public void deleteAllCarts() {
        repository.deleteAllCarts();
    }

    public LiveData<List<CartEntity>> getAllCarts() {
        return allCarts;
    }
    public LiveData<CartEntity> getCartByProductId(int productId) {
        return repository.getCartByProductId(productId);
    }
}