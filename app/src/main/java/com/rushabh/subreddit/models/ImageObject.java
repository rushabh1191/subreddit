package com.rushabh.subreddit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rushabh on 06/11/16.
 */

public class ImageObject implements Serializable{

    @SerializedName("source")
    ImageObject imageObject;

    @SerializedName("resolutions")
    ArrayList<ImageObject> imageObjects;

    @SerializedName("id")
    String id;
}
