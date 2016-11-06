package com.rushabh.subreddit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;

import com.rushabh.subreddit.models.Children;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SubredditFetcher.onPostsFetchListener {

    @BindView(R.id.et_search)
    AutoCompleteTextView autoCompleteTextView;

    SubredditFetcher fetcher;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        fetcher=new SubredditFetcher(this,this);
    }

    @OnClick(R.id.btn_search)
    void search(){

        String topic=autoCompleteTextView.getText().toString().trim();
        if(TextUtils.isEmpty(topic)){
            autoCompleteTextView.setError("Please enter topic");
        }
        else{
            performSearch(topic);
        }
    }

    void performSearch(String topic){
        progressDialog=ProgressDialog.show(this,"","Fetching...");
        fetcher.fetchSubreddit(topic);
    }


    @Override
    public void postFetched(ArrayList<Children> subredditPosts) {
        progressDialog.dismiss();
        Intent intent=PostListActivity.getListIntent(subredditPosts,this);
        startActivity(intent);
    }


    @Override
    public void failed() {
        progressDialog.dismiss();
    }
}
