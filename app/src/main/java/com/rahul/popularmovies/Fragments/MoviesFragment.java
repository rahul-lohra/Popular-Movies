package com.rahul.popularmovies.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.rahul.popularmovies.Adapter.MovieAdapter;
import com.rahul.popularmovies.AsyncRequest.AsyncMovieDetail;
import com.rahul.popularmovies.R;
import com.rahul.popularmovies.Utility.Constants;

/**
 * Created by Rahul on 4/13/2016.
 */
public class MoviesFragment extends Fragment {
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
        }
        return true;
    }
}
