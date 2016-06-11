package com.rahul.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.rahul.popularmovies.Activities.MovieDetailActivity;
import com.rahul.popularmovies.Model.Movie;
import com.rahul.popularmovies.R;
import com.rahul.popularmovies.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Rahul on 4/16/2016.
 */
public class MovieAdapter extends BaseAdapter {

    ArrayList<Movie> movieList;
    Context context;
    String TAG = "MovieAdapter";
    public MovieAdapter(ArrayList<Movie> movieList,Context context)
    {
        this.movieList = movieList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
        {
            convertView = li.inflate(R.layout.movie_poster,parent,false);
        }


        if(movieList!=null)
        {
            if(movieList.size()==0)
            {
                return null;
            }

            final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
            String str = movieList.get(position).getPoster_path();
            String img_path = str.substring(1);
            final String img_url = Constants.BASE_POSTER_URL+img_path;
//            Log.d(TAG,"Image url:"+img_url);
            Picasso.with(context).load(img_url).into(imageView);
            final String movie_title = movieList.get(position).getOriginal_title();
            final String overview = movieList.get(position).getOverview();
            final String release_date = movieList.get(position).getRelease_date();
            final String rating = movieList.get(position).getVote_average();


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra(Constants.MOVIE_TITLE,movie_title);
                    intent.putExtra(Constants.OVERVIEW,overview);
                    intent.putExtra(Constants.DATE,release_date);
                    intent.putExtra(Constants.RATING,rating);
                    intent.putExtra(Constants.POSTER_PATH,img_url);

                    Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    intent.putExtra("image",bitmap);
                    intent.putExtra(Constants.MOVIE_ID,movieList.get(position).getId());
                    context.startActivity(intent);

                }
            });
        }

        return convertView;
    }
}
