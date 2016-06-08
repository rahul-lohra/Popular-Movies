package com.rahul.popularmovies.Activities;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.rahul.popularmovies.Fragments.MoviesFragment;
import com.rahul.popularmovies.R;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"OnCreate");
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new MoviesFragment())
                    .commit();
        }
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

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_activity,menu);
//        return true;
//    }
}
