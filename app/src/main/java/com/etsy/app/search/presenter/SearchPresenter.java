package com.etsy.app.search.presenter;

public interface SearchPresenter {

    void search(String category, String query);

    void onDestroy();
}
