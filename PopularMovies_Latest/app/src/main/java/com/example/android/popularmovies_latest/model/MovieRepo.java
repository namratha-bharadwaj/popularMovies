package com.example.android.popularmovies_latest.model;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.popularmovies_latest.APIResponses.MovieApiResponse;
import com.example.android.popularmovies_latest.APIResponses.ReviewApiResponse;
import com.example.android.popularmovies_latest.APIResponses.TrailerApiResponse;
import com.example.android.popularmovies_latest.database.AppDatabase;
import com.example.android.popularmovies_latest.services.MoviesService;

import java.util.List;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class MovieRepo {
    private static final String TAG = MovieRepo.class.getName();
    private final String apiKey = MoviesService.API_KEY;
    private MoviesService moviesService;
    private final MutableLiveData<ReviewApiResponse> reviews;
    private MutableLiveData<MovieApiResponse> movies;
    private final MutableLiveData<TrailerApiResponse> trailers;
    private final AppDatabase database;

    public MovieRepo(AppDatabase database) {
        moviesService = new MoviesService();
        movies = new MutableLiveData<>();
        trailers = new MutableLiveData<>();
        reviews = new MutableLiveData<>();
        this.database = database;
    }

    public LiveData<MovieApiResponse> getMovies(String sortBy) {
        Call<MovieApiResponse> call = moviesService.getMovies(sortBy, apiKey);

        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movies.postValue(response.body());
                } else {
                    Log.e(TAG, "onResponse: getMovies failed or body is null: \n" + response.isSuccessful() + "\n" + response.toString());
                }
            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

        return movies;
    }

    public LiveData<TrailerApiResponse> getTrailers(Long movieId) {
        Call<TrailerApiResponse> call = moviesService.getTrailers(movieId, apiKey);
        call.enqueue(new Callback<TrailerApiResponse>() {
            @Override
            public void onResponse(Call<TrailerApiResponse> call, Response<TrailerApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    trailers.postValue(response.body());
                } else {
                    Log.e(TAG, "onResponse: getTrailers failed or body is null: \n" + response.isSuccessful() + "\n" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<TrailerApiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

        return trailers;
    }

    public LiveData<ReviewApiResponse> getReviews(Long movieId) {
        Call<ReviewApiResponse> call = moviesService.getReviews(movieId, apiKey);
        call.enqueue(new Callback<ReviewApiResponse>() {
            @Override
            public void onResponse(Call<ReviewApiResponse> call, Response<ReviewApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reviews.postValue(response.body());
                } else {
                    Log.e(TAG, "onResponse: getReviewList failed or body is null: \n" + response.isSuccessful() + "\n" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ReviewApiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

        return reviews;
    }

    public LiveData<Movie> getFavoriteById(final long movieId) {
        return database.favoriteDao().getFavoriteById(movieId);
    }

    public LiveData<List<Movie>> getAllFavorites() {
        return database.favoriteDao().getAllFavorites();
    }

}
