package com.rushabh.subreddit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rushabh.subreddit.models.Children;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostListActivity extends AppCompatActivity {

    private static final String LIST_OF_POSTS = "list_of_posts";
    @BindView(R.id.recycler_view_post_list)
    RecyclerView postListView;

    PostsAdapter adapter;

    ArrayList<Children> listOfPosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){
            listOfPosts= (ArrayList<Children>) bundle.getSerializable(LIST_OF_POSTS);
        }

        adapter=new PostsAdapter(this,listOfPosts);

        postListView.setLayoutManager(new LinearLayoutManager(this));
        postListView.setAdapter(adapter);



    }

    public static Intent getListIntent(ArrayList<Children> listOfPost, Context context){
        Intent intent=new Intent(context,PostListActivity.class);
        intent.putExtra(LIST_OF_POSTS,listOfPost);
        return intent;
    }
}
