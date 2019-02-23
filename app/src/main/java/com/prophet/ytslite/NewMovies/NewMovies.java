package com.prophet.ytslite.NewMovies;

public class NewMovies {

    private String id;
    private String url;
    private String title;
    private String year;
    private String ratings;
    private String runtime;
    private String genre;

    public NewMovies(String mId, String mUrl, String mTitle, String mYear, String mRatings, String mRuntime, String mGenre)
    {
        id = mId;
        url = mUrl;
        title = mTitle;
        year = mYear;
        ratings = mRatings;
        runtime = mRuntime;
        genre = mGenre;
    }

    public String getId()
    {
        return id;
    }
    public String getUrl() { return url; }
    public String getTitle()
    {
        return title;
    }
    public String getYear()
    {
        return year;
    }
    public String getRatings()
    {
        return ratings;
    }
    public String getRuntime()
    {
        return runtime;
    }
    public String getGenre()
    {
        return genre;
    }
}
