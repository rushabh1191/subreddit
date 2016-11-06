package com.rushabh.subreddit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rushabh on 06/11/16.
 */

public class SubredditPost implements Serializable {

    @SerializedName("id")
    public String id;
    @SerializedName("domain")
    public String domain;
    @SerializedName("author")
    public String author;
    @SerializedName("name")
    public String name;
    @SerializedName("thumbnail")
    public String thumbnail;
    @SerializedName("title")
    public String title;
    @SerializedName("created_utc")
    public long createdAt;
    @SerializedName("num_comments")
    public int numberOfComments;
    @SerializedName("subreddit")
    public String subreddit;
    @SerializedName("selftext_html")
    public String selfTextHtml;
    @SerializedName("url")
    public String url;
    @SerializedName("preview")
    public Preview preview;
}
