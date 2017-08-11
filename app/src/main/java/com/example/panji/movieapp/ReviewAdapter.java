package com.example.panji.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Panji on 11/08/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    Context context;
    List<Review> reviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }
    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_review,parent,false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.author.setText(reviewList
                .get(position)
                .getAuthor());
        holder.content.setText(reviewList
                .get(position)
                .getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }


    class ReviewHolder extends RecyclerView.ViewHolder{
        TextView author,content;

        public ReviewHolder(View itemView) {
            super(itemView);
            author =(TextView)itemView.findViewById(R.id.author);
            content =(TextView)itemView.findViewById(R.id.content);


        }
    }
}

