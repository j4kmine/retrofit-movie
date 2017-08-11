package com.example.panji.movieapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.R.attr.id;

/**
 * Created by Panji on 10/08/2017.
 */
//buat sharing database ke aplikasi lain nya
public class ContentProviderHerlper extends ContentProvider {

    public static  final  String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static  final  Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static  final  String PATH_MOVIE = "FAVORITE";
    public  static final  int CODE_FAVORITE=100;
    public  static final int CODE_FAVORITE_ID =101;
    private static  final UriMatcher sUriMatcher = buildUriMatcher();
    Favorite favorite;

    @Override
    public boolean onCreate() {
        favorite = new Favorite(getContext(),1);
        return true;
    }
    public  static  final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(PATH_MOVIE)
            .build();

    public  static UriMatcher buildUriMatcher(){
        final UriMatcher matcher= new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;
        matcher.addURI(authority,PATH_MOVIE,CODE_FAVORITE);
        matcher.addURI(authority,PATH_MOVIE+"/#",CODE_FAVORITE_ID);
        return matcher;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db =favorite.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match){
            case CODE_FAVORITE:
                retCursor = db.query(FavoriteTable.TBL_NAME,
                projection,
                selection,
                        selectionArgs,
                null,
                null,
                sortOrder);
                break;
            case CODE_FAVORITE_ID:
                String id = uri.getPathSegments().get(1);
                retCursor = db.query(FavoriteTable.TBL_NAME,
                        projection,
                        FavoriteTable.ID+"=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("UNKNOWN URI"+uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db =favorite.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri ReturnUri;
        long id =0;
        switch (match){
            case  CODE_FAVORITE:
                id = db.insert(FavoriteTable.TBL_NAME,null,values);
                if(id >0){
                    ReturnUri = ContentUris.withAppendedId(CONTENT_URI,id);
                }else{
                    throw new android.database.SQLException("failed"+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("UNKNOWN URI"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ReturnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db =favorite.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int taskDeleted;
        switch (match){
            case CODE_FAVORITE_ID:
                String id = uri.getPathSegments().get(1);
                taskDeleted = db.delete(FavoriteTable.TBL_NAME,FavoriteTable.ID+"=?",new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("UNKNOWN URI"+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
