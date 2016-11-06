package com.rushabh.subreddit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.rushabh.subreddit.models.Children;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostListActivity extends AppCompatActivity implements SubredditFetcher.onPostsFetchListener, View.OnClickListener {

    private static final String LIST_OF_POSTS = "list_of_posts";
    private static final String TOPIC = "topic";
    @BindView(R.id.recycler_view_post_list)
    RecyclerView postListView;

    PostsAdapter adapter;

    ArrayList<Children> listOfPosts;

    SubredditFetcher fetcher;
    String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        ButterKnife.bind(this);

        fetcher = new SubredditFetcher(this, this);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            listOfPosts = (ArrayList<Children>) bundle.getSerializable(LIST_OF_POSTS);
            topic = bundle.getString(TOPIC);
        }

        adapter = new PostsAdapter(this, listOfPosts, this);

        postListView.setLayoutManager(new LinearLayoutManager(this));
        postListView.setAdapter(adapter);

    }

    public static Intent getListIntent(ArrayList<Children> listOfPost, Context context, String topic) {
        Intent intent = new Intent(context, PostListActivity.class);
        intent.putExtra(LIST_OF_POSTS, listOfPost);
        intent.putExtra(TOPIC, topic);
        return intent;
    }

    @Override
    public void postFetched(ArrayList<Children> subredditPosts) {

    }

    @Override
    public void commentsFetched(ArrayList<Children> comments, Children post) {
        post.post.isMainPost = true;
        comments.add(0, post);
        Intent intent = PostDisplayActivity.getDetailIntent(comments, topic, this);
        startActivity(intent);
    }

    @Override
    public void failed() {

    }

    @Override
    public void onClick(View view) {

        PostViewHolder holder = (PostViewHolder) view.getTag();
        fetcher.fetchSubredditComments(topic, holder.id);
    }
}
