package com.example.android.popularmovies_latest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies_latest.APIResponses.ReviewApiResponse;
import com.example.android.popularmovies_latest.R;
import com.example.android.popularmovies_latest.adapters.ReviewsAdapter;
import com.example.android.popularmovies_latest.model.Movie;
import com.example.android.popularmovies_latest.viewModels.DetailActivityViewModel;
import com.example.android.popularmovies_latest.viewModels.DetailActivityViewModelFactory;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://image.tmdb.org/t/p/w342/";
    public static final String DETAIL_ACTIVITY_EXTRA = "movie";

    private ImageView detailActivityMoviePosterIv;
    private TextView detailActivityMovieTitleTv;
    private TextView detailActivityMovieReleaseDateTv;
    private TextView detailActivityMovieRatingTv;
    private ImageView detailActivityAddToFavIv;
    private TextView detailActivityMovieOverviewTv;
    private Button detailActivityWatchVideosButton;

    private RecyclerView detailActivityMovieReviewsRv;
    private ReviewsAdapter reviewAdapter;

    private DetailActivityViewModel detailActivityViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setTitle(R.string.details_screen_title);

        setupViews();
        Intent callingIntent = getIntent();
        Bundle extras = callingIntent.getBundleExtra(MainActivity.MAIN_ACTIVITY_EXTRA);
        Movie movie = extras.getParcelable(MainActivity.MAIN_ACTIVITY_EXTRA);
        setupClickListeners(movie);

        setupViewModel(movie);
        loadMovieData(movie);

    }

    private void setupViews() {
        setupFindByIDViews();
        setupRecyclerViews();
    }

    private void setupFindByIDViews() {
        detailActivityMoviePosterIv = findViewById(R.id.detail_activity_movie_poster_iv);
        detailActivityMovieTitleTv = findViewById(R.id.detail_activity_movie_title_tv);
        detailActivityMovieReleaseDateTv = findViewById(R.id.detail_activity_movie_release_date_tv);
        detailActivityMovieRatingTv = findViewById(R.id.detail_activity_user_rating_tv);
        detailActivityAddToFavIv = findViewById(R.id.detail_activity_add_to_fav_disabled_star_iv);
        detailActivityMovieOverviewTv = findViewById(R.id.detail_activity_movie_overview_tv);
        detailActivityWatchVideosButton = findViewById(R.id.detail_activity_watch_videos_button);
        detailActivityMovieReviewsRv = findViewById(R.id.detail_activity_reviews_rv);


    }

    private void setupRecyclerViews() {
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this);
        reviewAdapter = new ReviewsAdapter();

        detailActivityMovieReviewsRv.setLayoutManager(reviewLayoutManager);
        detailActivityMovieReviewsRv.setHasFixedSize(true);
        detailActivityMovieReviewsRv.setAdapter(reviewAdapter);
    }

    private void setupClickListeners(final Movie movie) {
        detailActivityAddToFavIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (detailActivityViewModel.isFavorite()) {
                    detailActivityViewModel.removeFavorite();
                    movieIsNotFavorite();
                    Toast.makeText(MovieDetailActivity.this, getApplicationContext().getString(R.string.toast_remove_favorites), Toast.LENGTH_SHORT).show();

                } else {
                    detailActivityViewModel.addFavorite();
                    movieIsFavorite();
                    Toast.makeText(MovieDetailActivity.this, getApplicationContext().getString(R.string.toast_add_favorites), Toast.LENGTH_SHORT).show();
                }
            }
        });

        detailActivityWatchVideosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(MovieDetailActivity.this, MovieTrailerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(DETAIL_ACTIVITY_EXTRA, movie);
                videoIntent.putExtra(DETAIL_ACTIVITY_EXTRA, bundle);
                startActivity(videoIntent);
            }
        });

    }

    private void setupViewModel(Movie movie) {
        detailActivityViewModel = ViewModelProviders.of(this, new DetailActivityViewModelFactory(getApplication(), movie)).get(DetailActivityViewModel.class);
        detailActivityViewModel.setMovie(movie);
        detailActivityViewModel.getFavorite().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                if (movie != null) {
                    movieIsFavorite();
                }
            }
        });

        detailActivityViewModel.getReviewList().observe(this, new Observer<ReviewApiResponse>() {
            @Override
            public void onChanged(ReviewApiResponse reviewApiResponse) {
                reviewAdapter.setReviews(reviewApiResponse.getReviews());
            }
        });

        detailActivityViewModel.getMovie().observe(MovieDetailActivity.this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie favorite) {
                if (detailActivityViewModel.isFavorite()) {
                    movieIsFavorite();
                } else {
                    movieIsNotFavorite();
                }
            }
        });
    }

    private void movieIsFavorite() {
        detailActivityAddToFavIv.setImageResource(R.drawable.ic_star_yellow_48dp);
        detailActivityViewModel.setFavorite(true);
    }

    private void movieIsNotFavorite() {
        detailActivityAddToFavIv.setImageResource(R.drawable.ic_star_border_yellow_48dp);
        detailActivityViewModel.setFavorite(false);
    }

    private void loadMovieData(Movie movie) {
        Picasso.get()
                .load(BASE_URL + movie.getPosterPath())
                .placeholder(R.color.black)
                .error(R.drawable.ic_launcher_foreground)
                .into(detailActivityMoviePosterIv);

        Date releaseDate = movie.getReleaseDate();

        detailActivityMovieTitleTv.setText(movie.getTitle());
        detailActivityMovieReleaseDateTv.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(releaseDate));
        detailActivityMovieRatingTv.setText(String.valueOf(movie.getVoteAverage()));
        detailActivityMovieOverviewTv.setText(movie.getOverview());

    }
}
