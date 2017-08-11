package com.example.panji.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Panji on 11/08/2017.
 */

public class Review {
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;


    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
