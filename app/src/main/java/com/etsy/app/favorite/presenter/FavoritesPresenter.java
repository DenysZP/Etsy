package com.etsy.app.favorite.presenter;

import com.etsy.app.network.models.Listing;

public interface FavoritesPresenter {

    void loadList();
    void deleteItem(Listing item);
    void returnItem(Listing item);
    void onDestroy();
}
