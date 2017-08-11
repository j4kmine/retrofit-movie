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

import com.bumptech.glide.Glide;

import java.util.List;

import static com.example.panji.movieapp.ApiInterface.BASE_IMG;

/**
 * Created by Panji on 06/08/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyHolder> {
    Context context;

    List<Trailer> trailerList;

    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        this.context = context;
        this.trailerList = trailerList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_trailer,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load("http://img.youtube.com/vi/"+trailerList.get(position).getKey()+"/0.jpg")

                .fitCenter()
                .into(holder.Thumbnails);
        holder.trailername.setText(trailerList
                .get(position)
                .getName());
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView trailername;
        ImageView Thumbnails;
        public MyHolder(View itemView) {
            super(itemView);
            trailername =(TextView)itemView.findViewById(R.id.name);
            Thumbnails =(ImageView)itemView.findViewById(R.id.gambar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String videoId = trailerList.get(pos).getKey();
                    Intent i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("vnd.youtube:"+videoId));
                    context.startActivity(i);
                }
            });

        }
    }
}
