package com.example.panji.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Panji on 06/08/2017.
 */

public class TrailerResult {
    @SerializedName("id")
    private String id_trailer;
    @SerializedName("results")
    private List<Trailer> results;

    public String getId_trailer() {
        return id_trailer;
    }

    public List<Trailer> getResults() {
        return results;
    }
}
