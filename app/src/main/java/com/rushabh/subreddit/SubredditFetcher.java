package com.rushabh.subreddit;

import android.content.Context;

/**
 * Created by rushabh on 06/11/16.
 */

public class SubredditFetcher {


    private Context context;

    private onPostsFetchListener postsFetchListener;
    public SubredditFetcher(Context context,onPostsFetchListener listener){
        this.context=context;
        postsFetchListener=listener;
    }



    public void fetchSubreddit(String topic){

    }

    public void fetchSubredditPosts(String topic){

    }


    public interface onPostsFetchListener{
        void postFetched();
        void failed();
    }
}
