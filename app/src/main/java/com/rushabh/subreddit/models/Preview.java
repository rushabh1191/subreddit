package com.rushabh.subreddit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rushabh on 06/11/16.
 */

public class Preview implements Serializable{

    @SerializedName("images")
    public ArrayList<ImageObject> imagesInformations;
}
