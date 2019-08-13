package com.example.android.popularmovies_latest.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.popularmovies_latest.model.Movie;

public class DetailActivityViewModelFactory implements ViewModelProvider.Factory {
    private final Movie movie;
    private final Application application;


    public DetailActivityViewModelFactory(Application application, Movie movie) {
        this.movie = movie;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailActivityViewModel(application, movie);
    }
}
