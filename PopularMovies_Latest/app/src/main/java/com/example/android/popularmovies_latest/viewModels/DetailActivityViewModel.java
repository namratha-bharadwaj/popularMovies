package com.example.android.popularmovies_latest.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.popularmovies_latest.APIResponses.ReviewApiResponse;
import com.example.android.popularmovies_latest.APIResponses.TrailerApiResponse;
import com.example.android.popularmovies_latest.database.AppDatabase;
import com.example.android.popularmovies_latest.database.AppExecutors;
import com.example.android.popularmovies_latest.model.Movie;
import com.example.android.popularmovies_latest.model.MovieRepo;

public class DetailActivityViewModel extends AndroidViewModel {
    private final MovieRepo movieRepo;
    private final AppDatabase database;

    private MutableLiveData<Movie> movie;
    private LiveData<ReviewApiResponse> reviewList;
    private LiveData<TrailerApiResponse> trailerList;
    private LiveData<Movie> isSaved;

    private boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public DetailActivityViewModel(Application application, Movie movie) {
        super(application);
        database = AppDatabase.getInstance(application);
        movieRepo = new MovieRepo(database);
        this.movie = new MutableLiveData<>();
        this.movie.postValue(movie);
        reviewList = new MutableLiveData<>();
        trailerList = new MutableLiveData<>();

    }

    public void setMovie(Movie movie) {
        this.movie.setValue(movie);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }

    public LiveData<ReviewApiResponse> getReviewList() {
        reviewList = movieRepo.getReviews(movie.getValue().getId());
        return reviewList;
    }

    public LiveData<TrailerApiResponse> getTrailerList() {
        trailerList = movieRepo.getTrailers(movie.getValue().getId());
        return trailerList;
    }

    public LiveData<Movie> getFavorite() {
        isSaved = movieRepo.getFavoriteById(movie.getValue().getId());
        return isSaved;
    }

    public void addFavorite() {
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.favoriteDao().insertFavorite(movie.getValue());
            }
        });
        isFavorite = true;
    }

    public void removeFavorite() {
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.favoriteDao().deleteFavorite(movie.getValue());
            }
        });
        isFavorite = false;
    }

}
