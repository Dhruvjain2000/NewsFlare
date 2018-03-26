package com.example.android.newsflare;

/**
 * Created by Dhruv Jain on 26-03-2018.
 */

public class NewsBrief {
    private String newsTitle;
    private String NewsImageURL;
    private String description;
    private String newsURL;
    private String publishedAt;

    public NewsBrief(String newsTitle, String newsImageURL) {
        this.newsTitle = newsTitle;
        NewsImageURL = newsImageURL;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDescription() {

        return description;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public  NewsBrief(){


    }

    public NewsBrief(String newsTitle, String newsImageURL, String description, String newsURL, String publishedAt) {
        this.newsTitle = newsTitle;
        NewsImageURL = newsImageURL;
        this.description = description;
        this.newsURL = newsURL;
        this.publishedAt = publishedAt;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsImageURL() {
        return NewsImageURL;
    }

    public void setNewsImageURL(String newsImageURL) {
        NewsImageURL = newsImageURL;
    }
}

