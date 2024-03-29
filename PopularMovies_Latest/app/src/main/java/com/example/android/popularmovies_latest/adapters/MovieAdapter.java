package com.example.android.popularmovies_latest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies_latest.R;
import com.example.android.popularmovies_latest.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    public static final String BASE_URL = "https://image.tmdb.org/t/p/w185/";

    private List<Movie> movies = new ArrayList<>();
    private OnMovieItemClickListener listener;

    public interface OnMovieItemClickListener {
        void onItemClick(Movie position);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        //FIXME: do not call this method
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.main_activity_rv_movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttach = false;

        View view = inflater.inflate(layoutId, viewGroup, shouldAttach);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {

        Movie movie = movies.get(position);
        Picasso.get()
                .load(BASE_URL + movie.getPosterPath())
                .placeholder(R.color.black)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView moviePoster;

        public MovieAdapterViewHolder(@NonNull final View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.iv_movie_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(movies.get(position));
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnMovieItemClickListener listener) {
        this.listener = listener;
    }
}
