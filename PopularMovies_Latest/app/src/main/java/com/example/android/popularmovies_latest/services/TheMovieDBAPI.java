package com.example.android.popularmovies_latest.services;

import com.example.android.popularmovies_latest.APIResponses.MovieApiResponse;
import com.example.android.popularmovies_latest.APIResponses.ReviewApiResponse;
import com.example.android.popularmovies_latest.APIResponses.TrailerApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDBAPI {
    @GET("{sort_by}")
    Call<MovieApiResponse> getMovies(@Path("sort_by") String sortBy, @Query("api_key") String key);

    @GET("{movie_id}/videos")
    Call<TrailerApiResponse> getTrailers(@Path("movie_id") Long movieId, @Query("api_key") String key);

    @GET("{movie_id}/reviews")
    Call<ReviewApiResponse> getReviews(@Path("movie_id") Long movieId, @Query("api_key") String key);
}
