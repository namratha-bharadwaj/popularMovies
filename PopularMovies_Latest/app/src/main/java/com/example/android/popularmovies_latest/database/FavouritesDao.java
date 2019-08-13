package com.example.android.popularmovies_latest.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.popularmovies_latest.model.Movie;

import java.util.List;

@Dao
public interface FavouritesDao {

    @Query("SELECT * FROM favorites ORDER BY id")
    LiveData<List<Movie>> getAllFavorites();

    @Query("SELECT * FROM favorites WHERE id = :id")
    LiveData<Movie> getFavoriteById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavorite(Movie favorite);

    @Delete
    void deleteFavorite(Movie favorite);

    @Query("DELETE FROM favorites")
    void deleteAllFavorites();

}
