package com.rahul.popularmovies.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.rahul.popularmovies.Contract.FavMovieContract;
import com.rahul.popularmovies.Database.FavMovieHelper;

/**
 * Created by Rahul on 6/9/2016.
 */
public class FavMovieProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavMovieHelper favMovieHelper;

    static final int FAV_MOVIE = 1;
    static final int FAV_MOVIE_ID = 2;

    private static final SQLiteQueryBuilder qb;

    static{
        qb = new SQLiteQueryBuilder();

        qb.setTables(FavMovieContract.FavMovieEntry.TABLE_NAME);

    }
    //    static final UriMatcher uriMatcher;
    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = FavMovieContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, FavMovieContract.PATH_FAV_MOVIE, FAV_MOVIE);
        matcher.addURI(authority, FavMovieContract.PATH_FAV_MOVIE+"/#", FAV_MOVIE_ID);
        return matcher;
    }

    private Cursor  getFavMovieById(Uri uri,String[] projection, String sortOrder)
    {
        String movieId = FavMovieContract.FavMovieEntry.getMovieIdFromUri(uri);
        return qb.query(favMovieHelper.getReadableDatabase(),
                projection,
                sFavMovieIdSelection,
                new String[]{movieId},
                null,
                null,
                sortOrder
        );
    }

    static String sFavMovieIdSelection = FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID + "=?";

    @Override
    public boolean onCreate() {
        favMovieHelper = new FavMovieHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        switch (sUriMatcher.match(uri))
        {
            case FAV_MOVIE:
                cursor = favMovieHelper.getReadableDatabase().query(
                        FavMovieContract.FavMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case FAV_MOVIE_ID:
                cursor = getFavMovieById(uri,projection,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match)
        {
            case FAV_MOVIE:
                return FavMovieContract.FavMovieEntry.CONTENT_TYPE;
            case FAV_MOVIE_ID:
                return FavMovieContract.FavMovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = favMovieHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case FAV_MOVIE:
                long rowID = db.insert(FavMovieContract.FavMovieEntry.TABLE_NAME, null, values);
                if (rowID > 0) {
                    returnUri = FavMovieContract.FavMovieEntry.buildFavMoviewUri(rowID);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = favMovieHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match)
        {
            case FAV_MOVIE_ID:
                rowsDeleted = db.delete(FavMovieContract.FavMovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
//                String movieId = uri.getPathSegments().get(1);
//                rowsDeleted = db.delete(FavMovieContract.FavMovieEntry.TABLE_NAME,
//                        FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID + "="+movieId+(!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
//                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
// Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = favMovieHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case FAV_MOVIE_ID:
                rowsUpdated = db.update(FavMovieContract.FavMovieEntry.TABLE_NAME,
                        values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
                if (rowsUpdated != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

        return rowsUpdated;
    }
}
