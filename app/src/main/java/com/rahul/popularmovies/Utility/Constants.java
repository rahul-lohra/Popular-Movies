package com.rahul.popularmovies.Utility;

import com.rahul.popularmovies.Model.Movie;
import com.rahul.popularmovies.Model.Trailer;

import java.util.ArrayList;

/**
 * Created by Rahul on 4/17/2016.
 */
public class Constants {
    public static final String MOVIE_ID = "movie_id" ;
    public static final String POSTER_PATH ="poster_path" ;
    public static String BASE_URL = "https://api.themoviedb.org";
    public static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185//";
    public static String API_KEY = "YOUR API KEY";
    public static String POPULAR = "popular";
    public static String MOVIE_TITLE = "movie_title";
    public static String RATING = "rating";
    public static String OVERVIEW = "overview";
    public static String DATE = "date";
    public static String TOP_RATED = "top_rated";
    public static boolean MOVIE_IS_LOADED = false;
    public static String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=";
    public static ArrayList<Movie> MOVIE_LIST= new ArrayList<>();
    public static ArrayList<Trailer> TRAILER_LIST= new ArrayList<>();

}
