package com.example.android.newsapp;

// Custom Class to hold relevant News Data

public class News {

    private String mNewsTile;
    private String mNewsDate;
    private String mSection;
    private String mURL;


    public News(String newsTitle, String newsDate, String section, String url) {
        mNewsTile = newsTitle;
        mNewsDate = newsDate;
        mSection = section;
        mURL = url;

    }

    public String getNewsTitle() {
        return mNewsTile;
    }

    public String getNewsDate() {
        return mNewsDate;
    }

    public String getSection() {
        return mSection;
    }

    public String getURL() {
        return mURL;
    }

}
