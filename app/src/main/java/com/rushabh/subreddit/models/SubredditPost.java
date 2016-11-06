package com.rushabh.subreddit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rushabh on 06/11/16.
 */

public class SubredditPost implements Serializable {

    @SerializedName("id")
    String id;
    @SerializedName("domain")
    String domain;
    @SerializedName("author")
    String author;
    @SerializedName("name")
    String name;
    @SerializedName("thumbnail")
    String thumbnail;
    @SerializedName("title")
    String title;
    @SerializedName("created_utc")
    long createdAt;
    @SerializedName("num_comments")
    int numberOfComments;
    @SerializedName("subreddit")
    String subreddit;
    @SerializedName("selftext_html")
    String selfTextHtml;
    @SerializedName("url")
    String url;
    @SerializedName("preview")
    Preview preview;
}
