package com.etsy.app.results.presenter;

import com.etsy.app.network.models.Listing;

public interface ResultsPresenter {

    void onItemsLoading(int page);
    void onAddItemToFavorite(Listing item, boolean isFavorite);
    void onDestroy();
}
