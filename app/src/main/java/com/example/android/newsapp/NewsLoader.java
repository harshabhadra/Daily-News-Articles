package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.List;

public class NewsLoader extends android.support.v4.content.AsyncTaskLoader {

    String url;

    public NewsLoader(Context context,String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        if (url == null) {
            return null;
        }
        List<News> result = null;
        try {
            result = QueryUtils.fetchNewsData(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
