package com.rahul.popularmovies.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul.popularmovies.R;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView tv_title,tv_rating,tv_release_date,tv_overview,tv_time;
    private ImageView imageView;
    private String title,rating,release_date,overview,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        init();

        Intent intent = getIntent();
        if (intent!=null)
        {

        }
    }
    public void  init()
    {
        tv_overview = (TextView)findViewById(R.id.tv_overview);
        tv_rating = (TextView)findViewById(R.id.tv_rating);
        tv_release_date = (TextView)findViewById(R.id.tv_release_date);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_title = (TextView)findViewById(R.id.tv_movie_name);
        imageView = (ImageView)findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent!=null)
        {
            title = intent.getStringExtra("movie_title");
            rating = intent.getStringExtra("rating");
            release_date = intent.getStringExtra("release_date");
            overview = intent.getStringExtra("overview");
//            title = intent.getStringExtra();

        }
    }
}
