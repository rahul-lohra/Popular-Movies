package com.rahul.popularmovies.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul.popularmovies.Adapter.TrailerAdapter;
import com.rahul.popularmovies.AsyncRequest.AsyncMovieTrailer;
import com.rahul.popularmovies.Contract.FavMovieContract;
import com.rahul.popularmovies.Fragments.MovieDetailFragment;
import com.rahul.popularmovies.R;
import com.rahul.popularmovies.Utility.Constants;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetail";
    private TextView tv_title, tv_rating, tv_release_date, tv_overview, tv_time;
    private ImageView imageView, imgHeart;
    private String title, rating, release_date, overview, time;
    private Bitmap bitmap;
    public static RecyclerView recyclerView;
    String movieId, posterPath;
    TrailerAdapter trailerAdapter;
    boolean isMovieFav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

//        if(savedInstanceState==null)
//        {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container,new MovieDetailFragment())
//                    .commit();
//        }
        init();
        checkForFavMovie();

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

    }

    private void deleteFavMovie() {
        int mRowsDeleted = 0;
        String[] mSelectionArgs = {
                movieId
        };
        String mSelectionClause = FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID + " LIKE ?";
        mRowsDeleted = getContentResolver().delete(FavMovieContract.FavMovieEntry.CONTENT_URI,
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
        Cursor cursor = getContentResolver().query(uri,
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

    public void init() {
        imgHeart = (ImageView) findViewById(R.id.img_heart);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_overview = (TextView) findViewById(R.id.tv_overview);
        tv_rating = (TextView) findViewById(R.id.tv_rating);
        tv_release_date = (TextView) findViewById(R.id.tv_release_date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_title = (TextView) findViewById(R.id.tv_movie_name);
        imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra(Constants.MOVIE_TITLE);
            rating = intent.getStringExtra(Constants.RATING);
            release_date = intent.getStringExtra(Constants.DATE);
            overview = intent.getStringExtra(Constants.OVERVIEW);
            bitmap = (Bitmap) intent.getParcelableExtra("image");
            movieId = intent.getStringExtra(Constants.MOVIE_ID);
            posterPath = intent.getStringExtra(Constants.POSTER_PATH);

            updateView();
            loadTrailer();
        }
    }

    private void loadTrailer() {
        AsyncMovieTrailer asyncMovieTrailer = new AsyncMovieTrailer(MovieDetailActivity.this);
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


        uri = getContentResolver().insert(FavMovieContract.FavMovieEntry.CONTENT_URI, cv);

        if (uri != null) {
            Log.d(TAG, "New Uri:" + uri.getPath() + "");
            markFavourite();

        } else {
            Log.d(TAG, "Uri is null");
        }

    }

}
