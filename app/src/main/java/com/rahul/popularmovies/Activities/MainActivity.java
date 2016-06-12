package com.rahul.popularmovies.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.rahul.popularmovies.Fragments.MovieDetailFragment;
import com.rahul.popularmovies.Fragments.MoviesFragment;
import com.rahul.popularmovies.R;
import com.rahul.popularmovies.Utility.Constants;

public class MainActivity extends AppCompatActivity implements MoviesFragment.Callback {

    String TAG = "MainActivity";
    private boolean mTwoPane;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"OnCreate");
//        if(savedInstanceState==null)
//        {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container,new MoviesFragment())
//                    .commit();
//        }

        if((findViewById(R.id.detail_movie)!=null))
        {
            mTwoPane = true;
            if(savedInstanceState==null)
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_movie,new MovieDetailFragment(),DETAILFRAGMENT_TAG)
                        .commit();
            }
        }else{
            mTwoPane = false;
        }

        Log.d(TAG,"isTwoPane:"+mTwoPane+"");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.d(TAG, "OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d(TAG, "OnResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Log.d(TAG, "OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.d(TAG, "OnDestroy");
    }

    @Override
    public void onItemSelected(String poster_path,
                               String overview,
                               String release_date,
                               String original_title,
                               String vote_average,
                               String id,
                               Bitmap bitmap) {

        Bundle args = new Bundle();
        args.putString(Constants.MOVIE_TITLE,original_title);
        args.putString(Constants.OVERVIEW,overview);
        args.putString(Constants.DATE,release_date);
        args.putString(Constants.RATING,vote_average);
        args.putString(Constants.POSTER_PATH,poster_path);
        args.putParcelable("image",bitmap);
        args.putString(Constants.MOVIE_ID,id);

        if(mTwoPane)
        {
            Log.d(TAG,"Two Pane");

            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            movieDetailFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_movie,movieDetailFragment,DETAILFRAGMENT_TAG)
                    .commit();
        }else {
            Log.d(TAG,"Single Pane");
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
//            intent.putExtra(Constants.MOVIE_TITLE,original_title);
//            intent.putExtra(Constants.OVERVIEW,overview);
//            intent.putExtra(Constants.DATE,release_date);
//            intent.putExtra(Constants.RATING,vote_average);
//            intent.putExtra(Constants.POSTER_PATH,poster_path);
//            intent.putExtra("image",bitmap);
//            intent.putExtra(Constants.MOVIE_ID,id);
            intent.putExtras(args);
            startActivity(intent);
        }
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main,menu);
//        return true;
//    }
}
