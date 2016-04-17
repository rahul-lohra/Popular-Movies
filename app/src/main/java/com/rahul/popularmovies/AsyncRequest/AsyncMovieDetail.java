package com.rahul.popularmovies.AsyncRequest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.rahul.popularmovies.Model.Movie;
import com.rahul.popularmovies.R;
import com.rahul.popularmovies.Utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Rahul on 4/16/2016.
 */
public class AsyncMovieDetail extends AsyncTask<String,String,String> {
    String response;
    String TAG = getClass().getSimpleName();
    ArrayList<Movie> list;
    Context context;
    int responseCode = 0;

    public AsyncMovieDetail(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection urlConnection = null;

        BufferedReader reader = null;
        String _concat = Constants.BASE_URL + "/3/movie/" + params[0] + "?";
        Uri builtUri = Uri.parse(_concat).buildUpon()
                .appendQueryParameter("api_key", Constants.API_KEY)
                .build();
        Log.d(TAG, "url:" + builtUri.toString());

        try {
            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                response = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                response = null;
            }
            responseCode = urlConnection.getResponseCode();
            response = buffer.toString();
            JSONObject jsonObject = new JSONObject(response);
//            Log.d(TAG, "Response:" + response);

            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); ++i) {
                String poster_path = jsonArray.getJSONObject(i).getString("poster_path");
                String overview = jsonArray.getJSONObject(i).getString("overview");
                String release_date = jsonArray.getJSONObject(i).getString("release_date");
                String original_title = jsonArray.getJSONObject(i).getString("original_title");
                String vote_average = jsonArray.getJSONObject(i).getString("vote_average");
//                public Movie(String poster_path, String overview, String release_date, String original_title, String vote_average) {

                Constants.MOVIE_LIST.add(new Movie(poster_path, overview, release_date, original_title, vote_average));
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (responseCode == 200) {
            Intent intent = new Intent("UpdateMovie");
            intent.putExtra("movieList",true);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        }

    }

}
