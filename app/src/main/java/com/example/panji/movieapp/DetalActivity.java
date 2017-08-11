package com.example.panji.movieapp;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.thumbnail;
import static com.example.panji.movieapp.ApiInterface.BASE_IMG;

public class DetalActivity extends AppCompatActivity {
    TrailerAdapter adapter;
    List<Trailer> trailerList;
    RecyclerView recyclerView;
    public int movie_id;
    RecyclerView.LayoutManager layoutManager;
    ImageView backdrop,poster;
    Result data;
    MenuItem item;
    TextView judul,tanggal,vote,synopsis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal);
        data = new GsonBuilder().create().fromJson(getIntent().getStringExtra("movie"),Result.class);
        backdrop = (ImageView)findViewById(R.id.backdrop);
        poster = (ImageView)findViewById(R.id.poster);
        judul = (TextView) findViewById(R.id.title);
        tanggal = (TextView) findViewById(R.id.tgl);
        vote = (TextView) findViewById(R.id.vote);
        synopsis = (TextView) findViewById(R.id.synopsis);
        recyclerView =(RecyclerView)findViewById(R.id.trailerView);
        String title = getIntent().getExtras().getString("title");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        trailerList = new ArrayList<>();
        adapter = new TrailerAdapter(this,trailerList);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new GridLayoutManager(DetalActivity.this,2));
        recyclerView.setAdapter(adapter);

        movie_id =data.getId();
//        String back = getIntent().getExtras().getString("backdrop");
//        Double votee = getIntent().getExtras().getDouble("vote");
//        String overview = getIntent().getExtras().getString("overview");
//        String thumbnail = getIntent().getExtras().getString("poster");
//        String release = getIntent().getExtras().getString("tanggal");
        Glide.with(this).load(BASE_IMG+data.getPosterPath()).into(poster);
        Glide.with(this).load(BASE_IMG+data.getBackdropPath()).into(backdrop);
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("MMM dd,yyyy");
        //nanti dulu
        try {

        }catch (Exception e){

        }
        judul.setText(data.getTitle());
        tanggal.setText("Tgl\n" + data.getReleaseDate());
        vote.setText("Vote\n" + data.getVoteCount());
        synopsis.setText(data.getOverview());
        loadJSON();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        item = menu.findItem(R.id.fav);
        item.setVisible(true);
        item.setIcon(!isFavorite()?R.drawable.unfav:R.drawable.fav_add);
        return true;
    }
    private  boolean isFavorite(){
        String stringId = String.valueOf(data.getId());
        Uri uri = ContentProviderHerlper.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor !=null){
            return cursor.getCount() >0;
        }
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fav ){
            if(isFavorite()){
                String movieid = String.valueOf(data.getId());
                Uri uri = ContentProviderHerlper.CONTENT_URI;
                uri = uri.buildUpon().appendPath(movieid).build();
                getContentResolver().delete(uri ,null,null);

            }else if (! isFavorite()){
                Uri uri = ContentProviderHerlper.CONTENT_URI;
                ContentValues values = new ContentValues();
                values.put(FavoriteTable.ID,data.getId());
                values.put(FavoriteTable.OVERVIEW,data.getOverview());
                values.put(FavoriteTable.RELEASE_DATE,data.getReleaseDate());
                values.put(FavoriteTable.ORIGINAL_TITLE,data.getOriginalTitle());
                values.put(FavoriteTable.POSTER,data.getPosterPath());
                values.put(FavoriteTable.BACKDROP_PATH,data.getBackdropPath());
                values.put(FavoriteTable.VOTE_AVERAGE,data.getVoteAverage());
                getContentResolver().insert(uri,values);
            }
            item.setIcon(!isFavorite()?R.drawable.unfav:R.drawable.fav_add);

        }else  if (item.getItemId() == R.id.shave ){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            i.putExtra(Intent.EXTRA_TEXT,"https://www.youtube.com/watch?v=".concat(trailerList.get(0).getKey()));
            startActivity(Intent.createChooser(i, "Share URL"));
//            Intent i = new Intent(Intent.ACTION_SEND);
//            i.setType("text/plain");
//            i.putExtra(Intent.EXTRA_TEXT,"https://www.youtube.com/watch?v=".concat(trailerList.get(0).getKey()));
//            startActivity(Intent.createChooser(i,"Trailer"));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  true;
    }

    private void loadJSON(){
        //int movie_id = getIntent().getExtras().getInt("id");
        ApiInterface apiInterface = ApiClient.getRetrofit()
                .create(ApiInterface.class);
        Call<TrailerResult> call = apiInterface.getTrailerMovie(movie_id);
        call.enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                trailerList = response.body().getResults();
                recyclerView.setAdapter(new TrailerAdapter(DetalActivity.this,trailerList));
                recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {

            }
        });

    }
}
