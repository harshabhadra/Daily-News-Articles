package com.example.android.newsapp;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private QueryUtils() {
    }

    //Return new URL object from the given URL
    public  static List<News>fetchNewsData(String reqUrl){
        URL url = createUrl(reqUrl);

        //Perform HTTP request to URL and recieve a JSONResponse
        String jsonresponse = null;
        try{
            jsonresponse = makeHttpRequest(url);
        }catch (IOException e){

            e.printStackTrace();
        }
        List<News>newsList = extractJsonResponse(jsonresponse);
        return newsList;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("tag", "Error with creating URL ", e);
        }
        return url;
    }
    /*
     * Make HTTP Request to the given Url and return Json response.
     * /
     *
     */

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * convert the {@link java.io.InputStream} into a string
     * which contains the whole json responsse from server
     */
    private static String readInputStream(InputStream inputStream)throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static List<News> extractJsonResponse(String newsJson){
        if (TextUtils.isEmpty(newsJson)){
            return null;
        }

        //Create an empty ArrayList so that we can add news to
        List<News> news = new ArrayList<>();

        //Try to parse the Json response. If there is a problem with the way
        // it is formatted then a JSONException exception object will be thrown.
        try {

            //create a JSONObject from the sample JSONResponse String
            JSONObject baseJsonResponse = new JSONObject(newsJson);


            JSONArray results = baseJsonResponse.getJSONArray("articles");

            for(int i = 0; i<results.length(); i++){

                JSONObject currentJson = results.getJSONObject(i);

                JSONObject source = currentJson.getJSONObject("source");


                String publisher = source.getString("name");

                String description = currentJson.getString("description");

                String titile = currentJson.getString("title");

                String url = currentJson.getString("url");

                String converImage = currentJson.getString("urlToImage");

                String date = currentJson.getString("publishedAt");

                News news1 = new News(publisher,titile,description,url, converImage,date);

                news.add(news1);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
// Api key : 80c6439c95fb4f8ea3e7506f7cb635da
        return news;
    }
}
