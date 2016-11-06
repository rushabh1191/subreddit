package com.rushabh.subreddit;

import com.rushabh.subreddit.models.SubredditDataFromServer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rushabh on 06/11/16.
 */

public interface SubredditFetcherService {
    @GET("/{topic}/new.json")
    Call<SubredditDataFromServer> fetchSubreddit(@Path("topic") String topic);
}
