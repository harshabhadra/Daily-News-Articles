package com.example.android.newsapp;

public class News {

    private String author;
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String date;

    public News(String author, String title, String description, String url, String imageUrl, String date) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
