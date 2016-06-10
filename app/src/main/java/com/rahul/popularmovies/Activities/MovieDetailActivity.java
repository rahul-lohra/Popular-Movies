package com.rahul.popularmovies.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.rahul.popularmovies.R;
import com.rahul.popularmovies.Utility.Constants;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG ="MovieDetail" ;
    private TextView tv_title, tv_rating, tv_release_date, tv_overview, tv_time;
    private ImageView imageView, imgHeart;
    private String title, rating, release_date, overview, time;
    private Bitmap bitmap;
    public static RecyclerView recyclerView;
    String movieId;
    TrailerAdapter trailerAdapter;
    boolean isMovieFav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        init();

        checkForFavMovie();

    }

    private void checkForFavMovie()
    {
        Uri uri = FavMovieContract.FavMovieEntry.CONTENT_URI;
//        Uri URI = FavMovieContract.FavMovieEntry.CONTENT_URI.ap(CONTENT_URI, 2);

        String[] projection = {
                FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID,
        };

        String [] selectionArgs = {
                FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID,

        };

        Cursor cursor = getContentResolver().query(uri,
                projection,
                movieId+" = ?",
                selectionArgs,
                null);

        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                do{
                    Log.d(TAG,"FAV MOVIE");
                }while (cursor.moveToNext());
            }
        }else {
            Log.d(TAG,"NOT FAV MOVIE");

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

    public void heartClick(View view) {
        if (isMovieFav) {

            ((ImageView)view).setColorFilter(getResources().getColor(R.color.white),android.graphics.PorterDuff.Mode.MULTIPLY);
            isMovieFav = false;

        } else {
            ((ImageView)view).setColorFilter(getResources().getColor(R.color.grassyGreen),android.graphics.PorterDuff.Mode.MULTIPLY);
            isMovieFav = true;
        }

        saveFavMovie();
    }

    private void saveFavMovie(){
        Uri uri;
        ContentValues cv;
    }

}
