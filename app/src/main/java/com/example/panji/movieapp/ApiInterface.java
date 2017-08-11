package com.example.panji.movieapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Panji on 05/08/2017.
 */
public interface ApiInterface {

    public  static final String BASE_IMG="http://image.tmdb.org/t/p/w185/";
    final String DB_KEY_API="681c104bae7aeb5753bcb9c62fe0a9d8";
    @GET("popular?api_key="+DB_KEY_API)
    Call<Movie> getPopular();

    @GET("top_rated?api_key="+DB_KEY_API)
    Call<Movie> getTopRated();
    @GET("upcoming?api_key="+DB_KEY_API)
    Call<Movie> getUpcoming();
    @GET("now_playing?api_key="+DB_KEY_API)
    Call<Movie> getNowPlaying();
    @GET("{id_movie}/videos?api_key="+DB_KEY_API)
    Call<TrailerResult>getTrailerMovie(@Path("id_movie") int id);
    @GET("{id_movie}/review?api_key="+DB_KEY_API)
    Call<TrailerResult>getReview(@Path("id_movie") int id);

}
