package com.example.panji.movieapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.data;
import static android.R.attr.version;

/**
 * Created by Panji on 09/08/2017.
 */

public class Favorite extends SQLiteOpenHelper {
    private static Favorite ourInstance;
    public static final String DB_NAME ="movie.db";
    public  static final int DB_VERSI = 1;
    private SQLiteDatabase database;
    private Context contextt;

    public Favorite(Context context,int version) {
        super(context, DB_NAME, null, version);
        database = getWritableDatabase();
        contextt = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                            "CREATE TABLE "+FavoriteTable.TBL_NAME+"("+
                                 FavoriteTable.ID+" INTEGER PRIMARY KEY,"+
                                    FavoriteTable.POSTER+" STRING ,"+
                                    FavoriteTable.OVERVIEW+" STRING ,"+
                                    FavoriteTable.ORIGINAL_TITLE+" STRING ,"+
                                    FavoriteTable.BACKDROP_PATH+" STRING ,"+
                                    FavoriteTable.RELEASE_DATE+" STRING ,"+
                                    FavoriteTable.VOTE_AVERAGE+" REAL,"+
                                    "UNIQUE ( " + FavoriteTable.ID + ") ON CONFLICT REPLACE);"
                        ;

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
