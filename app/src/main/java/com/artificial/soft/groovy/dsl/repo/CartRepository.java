package com.artificial.soft.groovy.dsl.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.artificial.soft.groovy.dsl.database.DB;
import com.artificial.soft.groovy.dsl.database.dao.CartDao;
import com.artificial.soft.groovy.dsl.database.entity.CartEntity;

import java.util.List;

public class CartRepository {
    private CartDao cartDao;
    private LiveData<List<CartEntity>> allCarts;

    public CartRepository(Application application) {
        DB database = DB.getInstance(application);
        cartDao = database.cartDao();
        allCarts = cartDao.getAllCarts();
    }

    public void insert(CartEntity cart) {
        new InsertCartAsyncTask(cartDao).execute(cart);
    }

    public void update(CartEntity cart) {
        new UpdateCartAsyncTask(cartDao).execute(cart);
    }

    public void delete(CartEntity cart) {
        new DeleteCartAsyncTask(cartDao).execute(cart);
    }

    public void deleteAllCarts() {
        new DeleteAllCartsAsyncTask(cartDao).execute();
    }

    public LiveData<List<CartEntity>> getAllCarts() {
        return allCarts;
    }

    private static class InsertCartAsyncTask extends AsyncTask<CartEntity, Void, Void> {
        private CartDao cartDao;

        private InsertCartAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(CartEntity... carts) {
            cartDao.insert(carts[0]);
            return null;
        }
    }

    private static class UpdateCartAsyncTask extends AsyncTask<CartEntity, Void, Void> {
        private CartDao cartDao;

        private UpdateCartAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(CartEntity... carts) {
            cartDao.update(carts[0]);
            return null;
        }
    }

    private static class DeleteCartAsyncTask extends AsyncTask<CartEntity, Void, Void> {
        private CartDao cartDao;

        private DeleteCartAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(CartEntity... carts) {
            cartDao.delete(carts[0]);
            return null;
        }
    }

    private static class DeleteAllCartsAsyncTask extends AsyncTask<Void, Void, Void> {
        private CartDao cartDao;

        private DeleteAllCartsAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartDao.deleteAllCarts();
            return null;
        }
    }

    public LiveData<CartEntity> getCartByProductId(int productId) {
        return cartDao.getCartByProductId(productId);
    }
}