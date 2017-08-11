package com.example.panji.movieapp;

import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by Panji on 10/08/2017.
 */
//property untuk table
public class FavoriteTable implements BaseColumns {

    public static final String TBL_NAME ="FAVORITE";
    public  static  final String ID ="ID";
    public  static  final String POSTER ="POSTER";
    public  static  final String OVERVIEW ="OVERVIEW";
    public  static  final String ORIGINAL_TITLE ="ORIGINAL_TITLE";
    public  static  final String BACKDROP_PATH ="BACKDROP_PATH";
    public  static  final String VOTE_AVERAGE ="VOTE_AVERAGE";
    public  static  final String RELEASE_DATE ="RELEASE_DATE";

    public static Result assign(Cursor cursor){
        Result movie =new Result();
        movie.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(ORIGINAL_TITLE)));
        movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(BACKDROP_PATH)));
        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(POSTER)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
        movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
        movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(VOTE_AVERAGE)));
        return movie;



    }

}
