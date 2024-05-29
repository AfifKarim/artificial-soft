package com.artificial.soft.groovy.dsl.network;

import com.artificial.soft.groovy.dsl.database.model.CategoryResponse;
import com.artificial.soft.groovy.dsl.database.model.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("api/product-by-category/{category_id}")
    Call<ProductResponse> getProductsByCategory(@Path("category_id") int categoryId);

    @GET("api/get-categories")
    Call<CategoryResponse> getCategories();
}
