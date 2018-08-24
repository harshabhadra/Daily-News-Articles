package com.example.android.newsapp;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
public class TimeLineFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final String HEAD_URL = "https://newsapi.org/v2/top-headlines?country=in&pageSize=100&apiKey=80c6439c95fb4f8ea3e7506f7cb635da";
    ListView newsListView;
    private static NewsAdapter newsAdapter;
    ProgressBar progressBar;
    TextView emptyText;

    public TimeLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_list,container,false);

        emptyText = rootView.findViewById(R.id.empty);
        progressBar = rootView.findViewById(R.id.progress_bar);
        newsListView = rootView.findViewById(R.id.list);
        newsAdapter = new NewsAdapter(getActivity(),new ArrayList<News>());
        newsListView.setAdapter(newsAdapter);

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

        if (networkInfo!=null && networkInfo.isConnected()){

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1,null,this);
        }else {
            progressBar.setVisibility(View.GONE);
            emptyText.setText("NO INTERNET CONNECTION");
        }


        return rootView;
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(getActivity(), HEAD_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {


        progressBar.setVisibility(View.GONE);
        newsAdapter.clear();
        if (data != null && !data.isEmpty()){
            newsAdapter.addAll(data);
        }else {
            emptyText.setText("NO DATAT FOUND");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        newsAdapter.clear();
    }
}
