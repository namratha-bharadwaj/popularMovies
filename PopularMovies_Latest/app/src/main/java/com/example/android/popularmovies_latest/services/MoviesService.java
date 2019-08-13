package com.example.android.popularmovies_latest.services;

import com.example.android.popularmovies_latest.APIResponses.MovieApiResponse;
import com.example.android.popularmovies_latest.APIResponses.ReviewApiResponse;
import com.example.android.popularmovies_latest.APIResponses.TrailerApiResponse;
import com.example.android.popularmovies_latest.BuildConfig;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesService implements TheMovieDBAPI {


    public static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final Retrofit retrofit;
    private TheMovieDBAPI movieApi;


    public MoviesService() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi = retrofit.create(TheMovieDBAPI.class);
    }

    @Override
    public Call<MovieApiResponse> getMovies(String sortBy, String key) {
        return movieApi.getMovies(sortBy, key);
    }

    @Override
    public Call<TrailerApiResponse> getTrailers(Long movieId, String key) {
        return movieApi.getTrailers(movieId, key);
    }

    @Override
    public Call<ReviewApiResponse> getReviews(Long movieId, String key) {
        return movieApi.getReviews(movieId,key);
    }
}
