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

    static String ENTERED_VALUE="entered_value";

    String topic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        fetcher=new SubredditFetcher(this,this);
        if(savedInstanceState!=null){
            String searchQuery=savedInstanceState.getString(ENTERED_VALUE);
            autoCompleteTextView.setText(searchQuery);
        }
    }

    @OnClick(R.id.btn_search)
    void search(){

        topic=autoCompleteTextView.getText().toString().trim();
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
        Intent intent=PostListActivity.getListIntent(subredditPosts,this,topic);
        startActivity(intent);
    }

    @Override
    public void commentsFetched(ArrayList<Children> comments, Children post) {
        post.post.isMainPost=true;
        comments.add(0,post);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
        outState.putString(ENTERED_VALUE,autoCompleteTextView.getText().toString());
    }

    @Override
    public void failed() {
        progressDialog.dismiss();
    }
}
