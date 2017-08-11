package com.example.panji.movieapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity {
    CustomAdapter adapter;
    RecyclerView mrecyclerview;
    List<Result>resultList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_popular){
            new RequestMoview().execute("popular");
        }else if(item.getItemId() == R.id.action_top_rated){
            new RequestMoview().execute("top_rated");
        }else if(item.getItemId() == R.id.action_now_playing){
            new RequestMoview().execute("now_playing");
        }else if(item.getItemId() == R.id.action_upcoming){
            new RequestMoview().execute("upcoming");
        }else if(item.getItemId() == R.id.action_favorite){
            getFavorite();
        }
        return super.onOptionsItemSelected(item);
    }
    public  void getFavorite(){
        Uri uri = ContentProviderHerlper.CONTENT_URI;
        Cursor cursor;
        cursor=getContentResolver().query(uri,null,null,null,null);
        resultList.clear();
        if(cursor.moveToFirst()){
            do {
                resultList.add(FavoriteTable.assign(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        adapter.setData(resultList);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mrecyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CustomAdapter(this,resultList);
        mrecyclerview.setAdapter(adapter);
        mrecyclerview.setLayoutManager(new GridLayoutManager(Main.this,2));
        new RequestMoview().execute("popular");

    }

    private class RequestMoview extends AsyncTask<String, Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            String kategori = params[0];
            if(kategori.equals("popular")){
                ApiInterface apiInterface =ApiClient.getRetrofit()
                         .create(ApiInterface.class);
                Call<Movie> call = apiInterface.getPopular();
                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Movie movie = response.body();
                        adapter.setData(movie.getResults());
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                });

            }else if(kategori.equals("top_rated")){
                ApiInterface apiInterface =ApiClient.getRetrofit()
                        .create(ApiInterface.class);
                Call<Movie> call = apiInterface.getTopRated();
                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Movie movie = response.body();
                        adapter.setData(movie.getResults());
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                });
            }else if(kategori.equals("upcoming")){
                ApiInterface apiInterface =ApiClient.getRetrofit()
                        .create(ApiInterface.class);
                Call<Movie> call = apiInterface.getUpcoming();
                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Movie movie = response.body();
                        adapter.setData(movie.getResults());
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                });
            }else if(kategori.equals("now_playing")){
                ApiInterface apiInterface =ApiClient.getRetrofit()
                        .create(ApiInterface.class);
                Call<Movie> call = apiInterface.getNowPlaying();
                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Movie movie = response.body();
                        adapter.setData(movie.getResults());
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                });
            }
            return null;
        }
    }
}
