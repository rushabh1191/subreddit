package com.rushabh.subreddit.network;

import com.rushabh.subreddit.models.SubredditDataFromServer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rushabh on 06/11/16.
 */

public interface SubredditFetcherService {
    @GET("/r/{topic}/new.json")
    Call<SubredditDataFromServer> fetchSubreddit(@Path("topic") String topic);

    @GET("/r/{topic}/comments/{id}/new.json")
    Call<ArrayList<SubredditDataFromServer>> fetchSubredditComments(@Path("topic") String topic,
                                                                    @Path("id") String id);
}
