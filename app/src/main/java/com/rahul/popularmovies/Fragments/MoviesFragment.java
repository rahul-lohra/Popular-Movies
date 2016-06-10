package com.rahul.popularmovies.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.rahul.popularmovies.Adapter.MovieAdapter;
import com.rahul.popularmovies.AsyncRequest.AsyncMovieDetail;
import com.rahul.popularmovies.Contract.FavMovieContract;
import com.rahul.popularmovies.Model.Movie;
import com.rahul.popularmovies.R;
import com.rahul.popularmovies.Utility.Constants;

import java.util.ArrayList;

/**
 * Created by Rahul on 4/13/2016.
 */
public class MoviesFragment extends Fragment {
    private static final String TAG = "MoviesFragment";
    GridView gridView;
    MovieAdapter movieAdapter;
    private boolean isReceiverRegistered;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            intent.getBundleExtra();
            boolean b = intent.getBooleanExtra("movieList",false);
            if(b)
            {
                //setAdapter
                movieAdapter = new MovieAdapter(Constants.MOVIE_LIST,getActivity());
                gridView.setAdapter(movieAdapter);
            }
        }

    };

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onPause();
        registerReceiver();
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,
                    new IntentFilter("UpdateMovie"));
            isReceiverRegistered = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movies_fragment, container, false);
        gridView = (GridView)rootView.findViewById(R.id.gridview);
        movieAdapter = new MovieAdapter(Constants.MOVIE_LIST,getActivity());
        gridView.setAdapter(movieAdapter);
        if(!Constants.MOVIE_IS_LOADED)
        {
            new AsyncMovieDetail(getActivity()).execute(Constants.POPULAR);
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.sort_popular:
                new AsyncMovieDetail(getActivity()).execute(Constants.POPULAR);
                break;
            case R.id.sort_top_rateed:
                new AsyncMovieDetail(getActivity()).execute(Constants.TOP_RATED);
                break;
            case R.id.sort_fav_rateed:
                // ......
                loadFavouriteMovies();
        }
        return true;
    }

    private void loadFavouriteMovies()
    {
//        String URL = FavMovieContract.FavMovieEntry
        Uri favMovies = FavMovieContract.FavMovieEntry.CONTENT_URI;

        Cursor cursor = getActivity().getContentResolver().query(favMovies, null, null, null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                Constants.MOVIE_LIST = new ArrayList<Movie>();
                do {
                    String id = cursor.getString(cursor.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_MOVIE_ID));
                    String poster_path = cursor.getString(cursor.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_POSTER_PATH));
                    String overview = cursor.getString(cursor.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_OVERVIEW));
                    String release_date = cursor.getString(cursor.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_RELEASE_DATE));
                    String original_title = cursor.getString(cursor.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_ORIGINAL_TITLE));
                    String vote_average = cursor.getString(cursor.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_VOTE_AVERAGE));
                    Constants.MOVIE_LIST.add(new Movie(poster_path, overview, release_date, original_title, vote_average,id));
                }while (cursor.moveToNext());


            }
            movieAdapter = new MovieAdapter(Constants.MOVIE_LIST,getActivity());
            gridView.setAdapter(movieAdapter);

        }else {
            Log.d(TAG,"No FAV MOVIES!!");
        }


    }
}
