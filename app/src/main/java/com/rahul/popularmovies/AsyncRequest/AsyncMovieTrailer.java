package com.rahul.popularmovies.AsyncRequest;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.rahul.popularmovies.Activities.MovieDetailActivity;
import com.rahul.popularmovies.Adapter.TrailerAdapter;
import com.rahul.popularmovies.Model.Movie;
import com.rahul.popularmovies.Utility.Constants;
import com.rahul.popularmovies.Model.Trailer;

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
 * Created by rahulkumarlohra on 07/06/16.
 */
public class AsyncMovieTrailer extends AsyncTask<String,String,String> {

    int responseCode = 0;
    Context context;
    String TAG = "AsyncMovieTrailer";
    String response= "";
    TrailerAdapter trailerAdapter;

    public AsyncMovieTrailer(Context context)
    {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection urlConnection = null;

        BufferedReader reader = null;
        String _concat = Constants.BASE_URL + "/3/movie/" + params[0] + "/videos?";
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
            JSONArray jsonArray = jsonObject.getJSONArray("results");
//            Log.d(TAG,"response:"+response);
//            Constants.MOVIE_LIST = new ArrayList<Movie>();
            for (int i = 0; i < jsonArray.length(); ++i) {
//                String poster_path = jsonArray.getJSONObject(i).getString("poster_path");
//                String overview = jsonArray.getJSONObject(i).getString("overview");
//                String release_date = jsonArray.getJSONObject(i).getString("release_date");
//                String original_title = jsonArray.getJSONObject(i).getString("original_title");
//                String vote_average = jsonArray.getJSONObject(i).getString("vote_average");
//                Constants.MOVIE_LIST.add(new Movie(poster_path, overview, release_date, original_title, vote_average));

                String name = jsonArray.getJSONObject(i).getString("name");
                String key = jsonArray.getJSONObject(i).getString("key");
                Constants.TRAILER_LIST.add(new Trailer(key,name));
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

        trailerAdapter = new TrailerAdapter(context,Constants.TRAILER_LIST);
        MovieDetailActivity.recyclerView.setAdapter(trailerAdapter);




    }
}
