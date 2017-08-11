package com.example.panji.movieapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Panji on 05/08/2017.
 */

public class ApiClient {

    public  static final String BASE_URL ="https://api.themoviedb.org/3/movie/";
    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        return retrofit;

    }
}
