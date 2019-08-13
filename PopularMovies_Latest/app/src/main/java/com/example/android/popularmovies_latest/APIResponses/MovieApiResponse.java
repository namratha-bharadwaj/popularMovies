package com.example.android.popularmovies_latest.APIResponses;

import androidx.annotation.NonNull;

import com.example.android.popularmovies_latest.model.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieApiResponse {

    @Expose
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("results")
    private List<Movie> movies;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    @NonNull
    public String toString() {

        return "Page: " + page + "\n" +
                "Total Pages: " + totalPages + "\n" +
                "Total Results: " + totalResults + "\n" +
                "Movies: " + movies;
    }
}