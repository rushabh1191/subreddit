package com.rushabh.subreddit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.rushabh.subreddit.models.Children;
import com.rushabh.subreddit.models.SearchHistory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements SubredditFetcher.onPostsFetchListener {

    @BindView(R.id.et_search)
    AutoCompleteTextView autoCompleteTextView;

    SubredditFetcher fetcher;

    ProgressDialog progressDialog;

    static String ENTERED_VALUE = "entered_value";

    String topic;

    ArrayList<SearchHistory> searchHistoryArrayList = new ArrayList<>();

    ArrayAdapter<SearchHistory> adapter;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        fetcher = new SubredditFetcher(this, this);
        if (savedInstanceState != null) {
            String searchQuery = savedInstanceState.getString(ENTERED_VALUE);
            autoCompleteTextView.setText(searchQuery);
        }

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        autoCompleteTextView.setThreshold(1);


        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                findViewById(R.id.btn_search).performClick();
                return true;
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                performSearch(adapter.getItem(i).toString());
            }
        });

    }

    void makeArrayListFromRealm(RealmResults<SearchHistory> searchHistories) {
        searchHistoryArrayList.clear();
        for (int i = 0; i < searchHistories.size(); i++) {
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setSearchQuery(searchHistories.get(i).toString());
            searchHistoryArrayList.add(searchHistory);
        }

        adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, searchHistoryArrayList);

        autoCompleteTextView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_search)
    void search() {

        topic = autoCompleteTextView.getText().toString().trim();
        if (TextUtils.isEmpty(topic)) {
            autoCompleteTextView.setError("Please enter topic");
        } else {
            realm.beginTransaction();
            SearchHistory s = realm.createObject(SearchHistory.class);
            s.setSearchQuery(topic);
            realm.commitTransaction();
            performSearch(topic);
        }
    }

    void performSearch(String topic) {
        this.topic=topic;
        progressDialog = ProgressDialog.show(this, "", "Fetching...");
        fetcher.fetchSubreddit(topic);
    }


    @Override
    public void postFetched(ArrayList<Children> subredditPosts) {
        progressDialog.dismiss();
        if(subredditPosts.size()>0){
            Intent intent = PostListActivity.getListIntent(subredditPosts, this, topic);
            startActivity(intent);
        }
        else{
            new ConfirmationWindow(this,"Nothing found","No subreddit posts found","Ok","");
        }

    }

    @Override
    public void commentsFetched(ArrayList<Children> comments, Children post) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        RealmQuery<SearchHistory> query = realm.where(SearchHistory.class);
        RealmResults<SearchHistory> results = query.findAll();
        makeArrayListFromRealm(results);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        outState.putString(ENTERED_VALUE, autoCompleteTextView.getText().toString());
    }

    @Override
    public void failed(String message) {
        progressDialog.dismiss();
        new ConfirmationWindow(this,"Error",message,"Ok","");
    }
}


