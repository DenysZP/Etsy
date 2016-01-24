package com.etsy.app.search.view;

import com.etsy.app.network.models.Goods;

public interface SearchView {

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void showResults(Goods goods);
}
