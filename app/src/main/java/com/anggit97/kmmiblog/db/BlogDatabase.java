package com.anggit97.kmmiblog.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.anggit97.kmmiblog.db.dao.PostDao;
import com.anggit97.kmmiblog.db.entity.PostEntity;

/**
 * Created by Anggit Prayogo on 03,September,2021
 * GitHub : https://github.com/anggit97
 */
@Database(entities = PostEntity.class, exportSchema = false, version = 1)
public abstract class BlogDatabase extends RoomDatabase {
    private static final String DB_NAME = "post_db";
    private static BlogDatabase instance;

    public static synchronized BlogDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), BlogDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract PostDao postDao();
}
