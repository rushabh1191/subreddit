package com.rushabh.subreddit.models;

import io.realm.RealmObject;

/**
 * Created by rushabh on 06/11/16.
 */

public class SearchHistory extends RealmObject {
    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    public String toString() {
        return searchQuery;
    }

    String searchQuery;
}
