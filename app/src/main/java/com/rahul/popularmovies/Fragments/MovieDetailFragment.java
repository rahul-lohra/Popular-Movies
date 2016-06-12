package com.rahul.popularmovies.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rahul.popularmovies.R;

/**
 * Created by rahulkumarlohra on 11/06/16.
 */
public class MovieDetailFragment extends Fragment {

    String TAG = "MovieDetailFrag";
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



            return rootView;
        }else {
            Log.d(TAG,"Bundle is empty");
            return null;
        }
//        return null;
    }
}
