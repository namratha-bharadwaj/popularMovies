package com.example.android.popularmovies_latest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies_latest.R;
import com.example.android.popularmovies_latest.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewAdapterViewHolder> {

    private List<Review> reviewList = new ArrayList<>();

    public void setReviews(List<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.movie_detail_activity_rv_review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttach = false;

        View view = inflater.inflate(layoutId, parent, shouldAttach);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.detailActivityReviewItemAuthorTv.setText(review.author);
        holder.detailActivityReviewItemReviewTv.setText(review.content);
    }

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView detailActivityReviewItemAuthorTv;
        private TextView detailActivityReviewItemReviewTv;

        public ReviewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            detailActivityReviewItemAuthorTv = itemView.findViewById(R.id.review_item_author_tv);
            detailActivityReviewItemReviewTv = itemView.findViewById(R.id.review_item_review_tv);
        }
    }

    @Override
    public int getItemCount() {
        return (reviewList != null ? reviewList.size() : 0);
    }
}
