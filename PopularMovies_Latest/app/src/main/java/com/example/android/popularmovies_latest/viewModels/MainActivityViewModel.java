package com.example.android.popularmovies_latest.viewModels;

import android.app.Application;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.android.popularmovies_latest.APIResponses.MovieApiResponse;
import com.example.android.popularmovies_latest.database.AppDatabase;
import com.example.android.popularmovies_latest.model.Movie;
import com.example.android.popularmovies_latest.model.MovieRepo;

import java.util.List;

import javax.inject.Inject;

public class MainActivityViewModel extends AndroidViewModel {

    private final MovieRepo movieRepo;
    private LiveData<MovieApiResponse> movieList;
    private final AppDatabase database;
    private final MutableLiveData<String> sortBy;
    private final LiveData<List<Movie>> favorites;

    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private int selectedMenu;

    //TODO: no dependecy injection, refactor
    public MainActivityViewModel(Application application) {
        super(application);
        database = AppDatabase.getInstance(application);
        movieRepo = new MovieRepo(database);
        movieList = new MutableLiveData<>();
        sortBy = new MutableLiveData<>();
        favorites = Transformations.switchMap(sortBy, new Function<String, LiveData<List<Movie>>>() {
            @Override
            public LiveData<List<Movie>> apply(String input) {
                return getAllFavorites();
            }
        });
        getPopularMovies();
    }

    public LiveData<MovieApiResponse> getMovieList() {
        return movieList;
    }

    @Inject
    public MainActivityViewModel(Application application, MovieRepo movieRepository) {
        super(application);
        movieRepo = movieRepository;
        movieList = new MutableLiveData<>();
        database = AppDatabase.getInstance(application);
        sortBy = new MutableLiveData<>();
        favorites = Transformations.switchMap(sortBy, new Function<String, LiveData<List<Movie>>>() {
            @Override
            public LiveData<List<Movie>> apply(String input) {
                return getAllFavorites();
            }
        });
        getPopularMovies();
    }

    public int getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(int selectedMenu) {
        this.selectedMenu = selectedMenu;
    }
    
    public LiveData<MovieApiResponse> getPopularMovies() {
        movieList = movieRepo.getMovies(POPULAR);
        return this.movieList;
    }

    public LiveData<MovieApiResponse> getTopRatedMovies() {
        movieList = movieRepo.getMovies(TOP_RATED);
        return this.movieList;
    }

    public LiveData<List<Movie>> getAllFavorites() {
        return movieRepo.getAllFavorites();
    }

    public LiveData<List<Movie>> getFavorites() {
        return favorites;
    }

    public void changeSort(String sort){
        sortBy.postValue(sort);
    }
}