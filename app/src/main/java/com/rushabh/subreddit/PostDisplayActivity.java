package com.rushabh.subreddit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rushabh.subreddit.models.Children;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDisplayActivity extends AppCompatActivity {

    private static final String POSTS_DATA = "post_data";
    private static final String TOPIC = "topic";


    @BindView(R.id.recycler_view_post_list)
    RecyclerView recyclerView;

    PostDetailsAdapter adapter;

    String topic;
    ArrayList<Children> listOfPosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle;
        if(savedInstanceState!=null){
            bundle=savedInstanceState;
        }
        else{
            bundle=getIntent().getExtras();
        }
        if(bundle!=null){
            listOfPosts= (ArrayList<Children>) bundle.getSerializable(POSTS_DATA);
            topic=bundle.getString(TOPIC);
        }

        adapter=new PostDetailsAdapter(listOfPosts,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(POSTS_DATA,listOfPosts);
        outState.putString(TOPIC,topic);
    }

    public static Intent getDetailIntent(ArrayList<Children> list, String topic, Context context){

        Intent intent=new Intent(context,PostDisplayActivity.class);
        intent.putExtra(POSTS_DATA,list);
        intent.putExtra(TOPIC,topic);
        return intent;
    }
}
