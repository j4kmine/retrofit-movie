package com.example.panji.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Panji on 11/08/2017.
 */

public class ReviewResult {
    @SerializedName("id")
    private String id_review;
    @SerializedName("results")
    private List<Review> results;


    public String getId_review() {
        return id_review;
    }

    public List<Review> getResults() {
        return results;
    }
}
