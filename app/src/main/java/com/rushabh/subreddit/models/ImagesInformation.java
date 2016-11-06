package com.rushabh.subreddit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rushabh on 06/11/16.
 */

public class ImagesInformation implements Serializable {

    @SerializedName("url")
    public String url;
    @SerializedName("width")
    int width;
    @SerializedName("height")
    int height;
}
