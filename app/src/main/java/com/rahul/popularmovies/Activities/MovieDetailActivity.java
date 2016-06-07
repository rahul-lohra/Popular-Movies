package com.rahul.popularmovies.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul.popularmovies.Adapter.TrailerAdapter;
import com.rahul.popularmovies.R;
import com.rahul.popularmovies.Utility.Constants;
import com.rahul.popularmovies.Utility.Trailer;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView tv_title,tv_rating,tv_release_date,tv_overview,tv_time;
    private ImageView imageView;
    private String title,rating,release_date,overview,time;
    private Bitmap bitmap;
    public static RecyclerView recyclerView;
    TrailerAdapter trailerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        init();

    }
    public void  init()
    {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_overview = (TextView)findViewById(R.id.tv_overview);
        tv_rating = (TextView)findViewById(R.id.tv_rating);
        tv_release_date = (TextView)findViewById(R.id.tv_release_date);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_title = (TextView)findViewById(R.id.tv_movie_name);
        imageView = (ImageView)findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent!=null)
        {
            title = intent.getStringExtra(Constants.MOVIE_TITLE);
            rating = intent.getStringExtra(Constants.RATING);
            release_date = intent.getStringExtra(Constants.DATE);
            overview = intent.getStringExtra(Constants.OVERVIEW);
            bitmap = (Bitmap)intent.getParcelableExtra("image");
            updateView();
        }
    }

    public void updateView()
    {
        tv_title.setText(title);
        tv_overview.setText(overview);
        tv_release_date.setText(release_date);
        tv_rating.setText(rating);
        imageView.setImageBitmap(bitmap);
    }
}
