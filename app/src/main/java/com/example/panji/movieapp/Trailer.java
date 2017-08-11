package com.example.panji.movieapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Panji on 06/08/2017.
 */

public class Trailer {
    @SerializedName("name")
    private String name;
    @SerializedName("key")
    private String key;

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
