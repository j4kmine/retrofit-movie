package com.example.panji.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;

import java.util.List;

import static com.example.panji.movieapp.ApiInterface.BASE_IMG;

/**
 * Created by Panji on 05/08/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;

    public CustomAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    List<Result>resultList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_poster,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext())
                   .load(BASE_IMG+resultList.get(position).getPosterPath())
                .centerCrop()
                .into(holder.Poster);
        holder.Title.setText(resultList.get(position).getOriginalTitle());
        holder.Vote.setText(Double.toString(resultList.get(position).getVoteAverage()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result data = resultList.get(position);
                Intent i = new Intent(holder.itemView.getContext(),DetalActivity.class);
                i.putExtra("movie" ,new GsonBuilder().create().toJson(data));
                holder.itemView.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
    public  void setData(List<Result> resultList){
        this.resultList = resultList;
        notifyDataSetChanged();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView Poster;
        TextView Title,Vote;
        public MyViewHolder(View itemView) {
            super(itemView);
            Poster = (ImageView)itemView.findViewById(R.id.poster);
            Title = (TextView) itemView.findViewById(R.id.title);
            Vote = (TextView) itemView.findViewById(R.id.vote);
//           itemView.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   int pos = getAdapterPosition();
//                   Intent i = new Intent(context ,DetalActivity.class);
//                   i.putExtra("poster",ApiInterface.BASE_IMG+resultList.get(pos).getPosterPath());
//                   i.putExtra("title",resultList.get(pos).getOriginalTitle());
//                   i.putExtra("backdrop",ApiInterface.BASE_IMG+resultList.get(pos).getBackdropPath());
//                   i.putExtra("tanggal",resultList.get(pos).getReleaseDate());
//                   i.putExtra("overview",resultList.get(pos).getOverview());
//                   i.putExtra("vote",resultList.get(pos).getVoteAverage());
//                   i.putExtra("id",resultList.get(pos).getId());
//                   context.startActivity(i);
//               }
//           });
        }
    }
}











