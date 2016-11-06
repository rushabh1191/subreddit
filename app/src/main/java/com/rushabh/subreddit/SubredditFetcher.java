package com.rushabh.subreddit;

import android.content.Context;
import android.util.Log;

import com.rushabh.subreddit.models.Children;
import com.rushabh.subreddit.models.SubredditDataFromServer;
import com.rushabh.subreddit.models.SubredditPost;
import com.rushabh.subreddit.network.RetrofitHelper;
import com.rushabh.subreddit.network.SubredditFetcherService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by rushabh on 06/11/16.
 */

public class SubredditFetcher {


    private Context context;

    private onPostsFetchListener postsFetchListener;


    private SubredditFetcherService fetcherService;

    public SubredditFetcher(Context context, onPostsFetchListener listener) {
        this.context = context;
        postsFetchListener = listener;
        Retrofit retrofit = RetrofitHelper.getRetrofitInstance();
        fetcherService = retrofit.create(SubredditFetcherService.class);
    }


    public void fetchSubreddit(String topic) {

        Call<SubredditDataFromServer> subredditDataFromServerCall = fetcherService.
                fetchSubreddit(topic);

        subredditDataFromServerCall.enqueue(new Callback<SubredditDataFromServer>() {
            @Override
            public void onResponse(Call<SubredditDataFromServer> call,
                                   Response<SubredditDataFromServer> response) {

                if (postsFetchListener != null) {
                    postsFetchListener.postFetched(response.body().data.childrenArrayList);
                }
            }

            @Override
            public void onFailure(Call<SubredditDataFromServer> call, Throwable t) {

            }
        });


    }

    public void fetchSubredditComments(String topic, String id) {
        Call<ArrayList<SubredditDataFromServer>> commentsFetcher = fetcherService.
                fetchSubredditComments(topic, id);
        commentsFetcher.enqueue(new Callback<ArrayList<SubredditDataFromServer>>() {
            @Override
            public void onResponse(Call<ArrayList<SubredditDataFromServer>> call,
                                   Response<ArrayList<SubredditDataFromServer>> response) {

                if (postsFetchListener != null) {
                    postsFetchListener.commentsFetched(response.body().get(1).data.childrenArrayList,
                            response.body().get(0).
                                    data.childrenArrayList.get(0));
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SubredditDataFromServer>> call, Throwable t) {

            }
        });

    }


    public interface onPostsFetchListener {
        void postFetched(ArrayList<Children> subredditPosts);

        void commentsFetched(ArrayList<Children> comments, Children post);

        void failed();
    }
}
