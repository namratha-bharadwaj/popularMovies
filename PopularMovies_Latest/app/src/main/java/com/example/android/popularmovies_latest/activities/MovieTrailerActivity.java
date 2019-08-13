package com.example.android.popularmovies_latest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.android.popularmovies_latest.APIResponses.TrailerApiResponse;
import com.example.android.popularmovies_latest.R;
import com.example.android.popularmovies_latest.adapters.TrailerAdapter;
import com.example.android.popularmovies_latest.model.Movie;
import com.example.android.popularmovies_latest.model.Trailer;
import com.example.android.popularmovies_latest.viewModels.DetailActivityViewModel;
import com.example.android.popularmovies_latest.viewModels.DetailActivityViewModelFactory;

public class MovieTrailerActivity extends AppCompatActivity {

    private RecyclerView movieTrailerRecyclerView;
    private TrailerAdapter trailerAdapter;
    private DetailActivityViewModel viewModel;
    public static final String YOUTUBE_URL = "www.youtube.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);
        setTitle(R.string.movie_trailer_activity_title);

        setupRecyclerView();
        Intent callingIntent = getIntent();
        Bundle extras = callingIntent.getBundleExtra(MovieDetailActivity.DETAIL_ACTIVITY_EXTRA);
        Movie movie = extras.getParcelable(MovieDetailActivity.DETAIL_ACTIVITY_EXTRA);
        setupClickListener();
        setupViewModel(movie);
    }

    private void setupRecyclerView() {
        movieTrailerRecyclerView = findViewById(R.id.movie_trailer_rv);
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this);
        trailerAdapter = new TrailerAdapter();

        movieTrailerRecyclerView.setLayoutManager(trailerLayoutManager);
        movieTrailerRecyclerView.setAdapter(trailerAdapter);

    }

    private void setupClickListener() {
        trailerAdapter.setOnItemClickListener(new TrailerAdapter.OnTrailerItemClickListener() {
            @Override
            public void onItemClick(Trailer trailer) {

                Uri trailerUri = new Uri.Builder()
                        .scheme("http")
                        .appendPath(YOUTUBE_URL)
                        .appendPath("watch")
                        .appendQueryParameter("v", trailer.key)
                        .build();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(trailerUri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void setupViewModel(Movie movie) {
        viewModel = ViewModelProviders.of(this, new DetailActivityViewModelFactory(getApplication(), movie)).get(DetailActivityViewModel.class);
        viewModel.setMovie(movie);
        viewModel.getTrailerList().observe(this, new Observer<TrailerApiResponse>() {
            @Override
            public void onChanged(TrailerApiResponse trailerApiResponse) {
                trailerAdapter.setTrailers(trailerApiResponse.getTrailers());
            }
        });

    }
}
