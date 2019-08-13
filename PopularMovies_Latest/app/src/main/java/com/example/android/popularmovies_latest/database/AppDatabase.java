package com.example.android.popularmovies_latest.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.android.popularmovies_latest.model.Movie;

import javax.inject.Singleton;

@Singleton
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class, GenreListTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "popularmovies";
    private static final Object LOCK = new Object();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }

    public abstract FavouritesDao favoriteDao();
}
