package com.artificial.soft.groovy.dsl.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class CartEntity {
    @PrimaryKey(autoGenerate = true)
    public int cart_id;
    public int cart_product_id;
    public String cart_product_name;
    public int cart_category_id;
    public String cart_category_name;
    public int cart_status;

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getCart_product_id() {
        return cart_product_id;
    }

    public void setCart_product_id(int cart_product_id) {
        this.cart_product_id = cart_product_id;
    }

    public String getCart_product_name() {
        return cart_product_name;
    }

    public void setCart_product_name(String cart_product_name) {
        this.cart_product_name = cart_product_name;
    }

    public int getCart_category_id() {
        return cart_category_id;
    }

    public void setCart_category_id(int cart_category_id) {
        this.cart_category_id = cart_category_id;
    }

    public String getCart_category_name() {
        return cart_category_name;
    }

    public void setCart_category_name(String cart_category_name) {
        this.cart_category_name = cart_category_name;
    }

    public int getCart_status() {
        return cart_status;
    }

    public void setCart_status(int cart_status) {
        this.cart_status = cart_status;
    }
}
