package com.rahul.popularmovies.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul.popularmovies.Adapter.TrailerAdapter;
import com.rahul.popularmovies.AsyncRequest.AsyncMovieTrailer;
import com.rahul.popularmovies.Contract.FavMovieContract;
import com.rahul.popularmovies.R;
import com.rahul.popularmovies.Utility.Constants;

/**
 * Created by rahulkumarlohra on 11/06/16.
 */
public class MovieDetailFragment extends Fragment {

    String TAG = "MovieDetailFrag";
    private TextView tv_title, tv_rating, tv_release_date, tv_overview, tv_time;
    private ImageView imageView, imgHeart;
    private String title, rating, release_date, overview, time;
    private Bitmap bitmap;
    public static RecyclerView recyclerView;
    String movieId, posterPath;
    TrailerAdapter trailerAdapter;
    boolean isMovieFav = false;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_movie_detail, container, false);

        Bundle arguments = getArguments();
        if(arguments!=null)
        {
            Log.d(TAG,"Data is Present in bundle");

            init(rootView,arguments);


            imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMovieFav) {
                    deleteFavMovie();
                } else {
                    saveFavMovie();
                }
            }
        });


            return rootView;
        }else {
            Log.d(TAG,"Bundle is empty");
            return null;
        }
    }

    private void init(View rootView ,Bundle arguments)
    {
        imgHeart = (ImageView)rootView. findViewById(R.id.img_heart);
        recyclerView = (RecyclerView)rootView. findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tv_overview = (TextView) rootView.findViewById(R.id.tv_overview);
        tv_rating = (TextView)rootView. findViewById(R.id.tv_rating);
        tv_release_date = (TextView)rootView. findViewById(R.id.tv_release_date);
        tv_time = (TextView)rootView. findViewById(R.id.tv_time);
        tv_title = (TextView) rootView.findViewById(R.id.tv_movie_name);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);


        title = arguments.getString(Constants.MOVIE_TITLE);
        rating = arguments.getString(Constants.RATING);
        release_date = arguments.getString(Constants.DATE);
        overview = arguments.getString(Constants.OVERVIEW);
        bitmap = (Bitmap) arguments.getParcelable("image");
        movieId = arguments.getString(Constants.MOVIE_ID);
        posterPath = arguments.getString(Constants.POSTER_PATH);

        Log.d(TAG,"movieId:"+movieId);
        Log.d(TAG,"title:"+title);


        updateView();
        loadTrailer();
        checkForFavMovie();


    }

    private void deleteFavMovie() {
        int mRowsDeleted = 0;
        String[] mSelectionArgs = {
                movieId
        };
        String mSelectionClause = FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID + " LIKE ?";
        mRowsDeleted = getActivity().getContentResolver().delete(FavMovieContract.FavMovieEntry.CONTENT_URI,
                mSelectionClause,
                mSelectionArgs
        );
        Log.d(TAG, "No of Rows Deleted:" + mRowsDeleted);
        if (mRowsDeleted > 0) {
            unMarkFavourite();
        }
    }

    private void markFavourite() {
        imgHeart.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        isMovieFav = true;
    }

    private void unMarkFavourite() {
        imgHeart.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        isMovieFav = false;
    }

    private void checkForFavMovie() {
        Uri uri = FavMovieContract.FavMovieEntry.CONTENT_URI;
        String[] projection = {
                FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID,
        };
        String[] selectionArgs = {
                movieId
        };
        Cursor cursor = getActivity().getContentResolver().query(uri,
                projection,
                FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID + " = ?",
                selectionArgs,
                null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Log.d(TAG, "FAV MOVIE");
                    markFavourite();
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "***111*** NOT A FAV MOVIE");
                unMarkFavourite();
            }
        } else {
            Log.d(TAG, "NOT FAV MOVIE");

        }


    }

    private void loadTrailer() {
        AsyncMovieTrailer asyncMovieTrailer = new AsyncMovieTrailer(getContext());
        asyncMovieTrailer.execute(movieId);
    }

    public void updateView() {
        tv_title.setText(title);
        tv_overview.setText(overview);
        tv_release_date.setText(release_date);
        tv_rating.setText(rating);
        imageView.setImageBitmap(bitmap);
    }


    private void saveFavMovie() {
        Uri uri;
        ContentValues cv = new ContentValues();
        cv.put(FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID, movieId);
        cv.put(FavMovieContract.FavMovieEntry.COLUMN_ORIGINAL_TITLE, title);
        cv.put(FavMovieContract.FavMovieEntry.COLUMN_OVERVIEW, overview);
        cv.put(FavMovieContract.FavMovieEntry.COLUMN_POSTER_PATH, posterPath);
        cv.put(FavMovieContract.FavMovieEntry.COLUMN_RELEASE_DATE, release_date);
        cv.put(FavMovieContract.FavMovieEntry.COLUMN_VOTE_AVERAGE, rating);


        uri = getActivity().getContentResolver().insert(FavMovieContract.FavMovieEntry.CONTENT_URI, cv);

        if (uri != null) {
            Log.d(TAG, "New Uri:" + uri.getPath() + "");
            markFavourite();

        } else {
            Log.d(TAG, "Uri is null");
        }

    }

    }
