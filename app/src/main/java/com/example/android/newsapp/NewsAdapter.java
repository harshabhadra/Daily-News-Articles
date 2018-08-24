package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter( Context context, ArrayList<News>news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);
        }

        News news = getItem(position);

        TextView headingText = listItemView.findViewById(R.id.author);
        headingText.setText(news.getAuthor());

        TextView titleText = listItemView.findViewById(R.id.title);
        titleText.setText(news.getTitle());

        TextView descriptionText = listItemView.findViewById(R.id.description);
        descriptionText.setText(news.getDescription());

        String dateAndTime = news.getDate();

        String date;
        String time;

        String[]parts = dateAndTime.split("T");


        date = parts[0];
        time = parts[1];

        TextView dateView = listItemView.findViewById(R.id.date);
        dateView.setText(date);

        TextView timeView  = listItemView.findViewById(R.id.time);
        timeView.setText(time);

        ImageView imageView = listItemView.findViewById(R.id.coverImage);
        Picasso.get().load(news.getImageUrl()).placeholder(R.drawable.news_app_icon).error(R.drawable.news_app_icon).into(imageView);

        return listItemView;
    }
}
