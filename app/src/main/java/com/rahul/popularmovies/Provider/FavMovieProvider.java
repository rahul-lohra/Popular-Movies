package com.rahul.popularmovies.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.rahul.popularmovies.Contract.FavMovieContract;
import com.rahul.popularmovies.Database.FavMovieHelper;

/**
 * Created by Rahul on 6/9/2016.
 */
public class FavMovieProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private FavMovieHelper favMovieHelper;

    static final int FAV_MOVIE = 1;
    static final int FAV_MOVIE_ID = 2;

//    static final UriMatcher uriMatcher;
    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = FavMovieContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority, "contacts", FAV_MOVIE);
        uriMatcher.addURI(authority, "contacts/#", FAV_MOVIE_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        favMovieHelper = new FavMovieHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
