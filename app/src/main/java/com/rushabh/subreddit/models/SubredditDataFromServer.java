package com.rushabh.subreddit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rushabh on 06/11/16.
 */

public class SubredditDataFromServer implements Serializable{
    @SerializedName("kind")
    public String kind;
    @SerializedName("data")
    public SubredditData data;
}
