package com.rushabh.subreddit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rushabh on 06/11/16.
 */

public class Children implements Serializable{
    @SerializedName("kind")
    String t3;
    @SerializedName("data")
    SubredditPost post;
}
