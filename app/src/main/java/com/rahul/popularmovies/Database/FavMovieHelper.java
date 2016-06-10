package com.rahul.popularmovies.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rahul.popularmovies.Contract.FavMovieContract;

/**
 * Created by Rahul on 6/8/2016.
 */
public class FavMovieHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";
    public FavMovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAV_MOVIE_TABLE = "CREATE TABLE " + FavMovieContract.FavMovieEntry.TABLE_NAME + " (" +
                FavMovieContract.FavMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID + " TEXT UNIQUE NOT NULL, " +
                FavMovieContract.FavMovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                FavMovieContract.FavMovieEntry.COLUMN_OVERVIEW + " REAL NOT NULL, " +
                FavMovieContract.FavMovieEntry.COLUMN_POSTER_PATH + " REAL NOT NULL " +
                FavMovieContract.FavMovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL " +

                " );";

        db.execSQL(SQL_CREATE_FAV_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavMovieContract.FavMovieEntry.TABLE_NAME);
        onCreate(db);


    }
}
