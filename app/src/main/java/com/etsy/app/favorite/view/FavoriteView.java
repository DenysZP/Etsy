package com.etsy.app.favorite.view;

import com.etsy.app.network.models.Listing;

import java.util.List;

public interface FavoriteView {

    void showProgress();
    void hideProgress();
    void showMessage();
    void setList(List<Listing> items);
}
