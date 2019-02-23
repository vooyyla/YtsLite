package com.prophet.ytslite.NewMovies;

import android.text.TextUtils;
import android.util.Log;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    private static final String LOG_TAG = Utility.class.getSimpleName();

    private static URL createUrl(String requestUrl)
    {
        URL url = null;
        if (requestUrl != null)
        {
            try
            {
                url = new URL(requestUrl);
            }catch (MalformedURLException e)
            {
                Log.e(LOG_TAG, "Error in creating URL. ", e);
            }
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null)
            {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static String makeHttpRequest(String requestUrl) throws IOException
    {
        URL url = createUrl(requestUrl);
        String jsonResponse = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        if (requestUrl != null)
        {
            try
            {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200)
                {
                    inputStream = httpURLConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                }else
                {
                    Log.e(LOG_TAG, "Error in request. Response code: "+ httpURLConnection.getResponseCode());
                }

            }catch (IOException e)
            {
                Log.e(LOG_TAG, "Error making url connection. ", e);
            }finally
            {
                if (inputStream != null)
                { inputStream.close(); }
                if (httpURLConnection != null)
                { httpURLConnection.disconnect(); }
            }
        }
        return jsonResponse;
    }

    private static List<NewMovies> extractFromJson(String jsonResponse)
    {
        if(TextUtils.isEmpty(jsonResponse))
        { return null; }
        List<NewMovies> moviesList = new ArrayList<>();
        try
        {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray root = data.getJSONArray("movies");
            for ( int i = 0 ; i < data.length(); i++)
            {
                JSONObject currentObject = root.getJSONObject(i);

                JSONObject idJson = currentObject.getJSONObject("id");
                int intId = idJson.getInt("id");
                String id = Integer.toString(intId);

                JSONObject urlJson = currentObject.getJSONObject("url");
                String url = urlJson.getString("url");

                JSONObject titleJson = currentObject.getJSONObject("title");
                String title = titleJson.getString("title");

                JSONObject yearJson = currentObject.getJSONObject("year");
                int intYear = yearJson.getInt("year");
                String year = Integer.toString(intYear);

                JSONObject ratingJson = currentObject.getJSONObject("rating");
                double doubleRating = ratingJson.getDouble("rating");
                String rating = Double.toString(doubleRating);

                JSONObject runtimeJson = currentObject.getJSONObject("runtime");
                int intRuntime = runtimeJson.getInt("runtime");
                String runtime = Integer.toString(intRuntime);

                JSONObject genreJson = currentObject.getJSONObject("genre");
                String genre = genreJson.getString("genre");

                NewMovies movie = new NewMovies(id, url, title, year, rating, runtime, genre);

                moviesList.add(movie);
            }

        }catch (JSONException e)
        {
            Log.e(LOG_TAG, "Error parsing json data! ", e);
        }
        return moviesList;
    }
}
