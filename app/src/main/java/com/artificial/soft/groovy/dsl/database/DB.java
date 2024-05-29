package com.artificial.soft.groovy.dsl.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.artificial.soft.groovy.dsl.database.dao.CartDao;
import com.artificial.soft.groovy.dsl.database.dao.CategoryDao;
import com.artificial.soft.groovy.dsl.database.dao.ProductDao;
import com.artificial.soft.groovy.dsl.database.entity.CartEntity;
import com.artificial.soft.groovy.dsl.database.entity.CategoryEntity;
import com.artificial.soft.groovy.dsl.database.entity.ProductEntity;

@Database(entities = {CategoryEntity.class, ProductEntity.class , CartEntity.class}, version = 1)
public abstract class DB extends RoomDatabase {

    public abstract CategoryDao categoryDao();
    public abstract ProductDao productDao();
    public abstract CartDao cartDao();
    private static volatile DB INSTANCE;

    public static synchronized DB getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (DB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DB.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
