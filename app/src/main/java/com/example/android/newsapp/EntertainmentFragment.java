package com.example.android.newsapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntertainmentFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>{
    private static final String NEWS_URL = "https://newsapi.org/v2/everything?";
    private static NewsAdapter newsAdapter;
    ListView newsListView;
    ProgressBar progressBar;
    TextView emptyText;

    public EntertainmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.news_list,container,false);

        progressBar = rootView.findViewById(R.id.progress_bar);
        newsListView = rootView.findViewById(R.id.list);
        newsAdapter = new NewsAdapter(getActivity(), new ArrayList<News>());
        newsListView.setAdapter(newsAdapter);
        emptyText = rootView.findViewById(R.id.empty);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                News news = newsAdapter.getItem(position);

                Uri newsUri = Uri.parse(news.getUrl());

                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(webIntent);
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
        }else {
            progressBar.setVisibility(View.GONE);
            emptyText.setText("NO INTERNET CONNECTION");
        }


      return rootView;
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = sharedPreferences.getString("order_by","publishedAt");
        Uri baseUri = Uri.parse(NEWS_URL);
        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("q", "movies");
        builder.appendQueryParameter("pageSize", "60");
        builder.appendQueryParameter("language", "en");
        builder.appendQueryParameter("sortBy", sortBy);
        builder.appendQueryParameter("apiKey", "80c6439c95fb4f8ea3e7506f7cb635da");
        return new NewsLoader(getActivity(), builder.toString());
        //https://newsapi.org/v2/everything?q=entertainment&language=en=sortBy=publishedAt&apikey=80c6439c95fb4f8ea3e7506f7cb635da
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {


        progressBar.setVisibility(View.GONE);
        newsAdapter.clear();
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }else {
            emptyText.setText("NO DATA FOUND");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {

        newsAdapter.clear();
    }
}
