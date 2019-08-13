package com.example.android.popularmovies_latest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.popularmovies_latest.APIResponses.MovieApiResponse;
import com.example.android.popularmovies_latest.R;
import com.example.android.popularmovies_latest.adapters.MovieAdapter;
import com.example.android.popularmovies_latest.model.Movie;
import com.example.android.popularmovies_latest.viewModels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RV_SPAN_COUNT = 2;
    public static final String MAIN_ACTIVITY_EXTRA = "movie";
    private static final String EXTRA_MENU_SELECTED = "MENU_SELECTED";
    private static final String EXTRA_SCROLL_STATE = "SCROLL_STATE";

    private RecyclerView mainActivityRv;
    private MovieAdapter moviePosterAdapter;
    private MainActivityViewModel mainActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        setupViewModel();

    }

    private void setupViews() {
        GridLayoutManager mainActivityLayoutManager = new GridLayoutManager(this, RV_SPAN_COUNT);
        moviePosterAdapter = new MovieAdapter();
        mainActivityRv = findViewById(R.id.main_activity_rv);
        mainActivityRv.setLayoutManager(mainActivityLayoutManager);

        mainActivityRv.setHasFixedSize(true);
        mainActivityRv.setAdapter(moviePosterAdapter);

        moviePosterAdapter.setOnItemClickListener(new MovieAdapter.OnMovieItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(MAIN_ACTIVITY_EXTRA, movie);
                intent.putExtra(MAIN_ACTIVITY_EXTRA, bundle);
                startActivity(intent);
            }
        });

    }

    private void setupViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getMovieList().observe(this, new Observer<MovieApiResponse>() {
            @Override
            public void onChanged(MovieApiResponse movieApiResponse) {
                moviePosterAdapter.setMovies(movieApiResponse.getMovies());
            }

        });
        mainActivityViewModel.getFavorites().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (mainActivityViewModel.getSelectedMenu() == 2) {
                    mainActivityViewModel.getMovieList().getValue().setMovies(movies);
                    moviePosterAdapter.setMovies(movies);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_categories, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int optionId = item.getItemId();

        switch (optionId) {
            case R.id.most_popular_movies_menu_item:
                mainActivityViewModel.setSelectedMenu(0);
                mainActivityViewModel.getPopularMovies();
                return true;

            case R.id.top_rated_movies_menu_iten:
                mainActivityViewModel.setSelectedMenu(1);
                mainActivityViewModel.getTopRatedMovies();
                return true;

            case R.id.favourite_movies_menu_item:
                mainActivityViewModel.setSelectedMenu(2);
                mainActivityViewModel.changeSort("Favorites");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Parcelable scrollState = mainActivityRv.getLayoutManager().onSaveInstanceState();
        outState.putInt(EXTRA_MENU_SELECTED, mainActivityViewModel.getSelectedMenu());
        outState.putParcelable(EXTRA_SCROLL_STATE, scrollState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable scrollState = savedInstanceState.getParcelable(EXTRA_SCROLL_STATE);
        mainActivityRv.getLayoutManager().onRestoreInstanceState(scrollState);
        mainActivityViewModel.setSelectedMenu(savedInstanceState.getInt(EXTRA_MENU_SELECTED));
    }
}
