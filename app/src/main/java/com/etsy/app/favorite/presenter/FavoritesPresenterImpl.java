package com.etsy.app.favorite.presenter;

import com.etsy.app.favorite.view.FavoriteView;
import com.etsy.app.network.models.Listing;

public class FavoritesPresenterImpl implements FavoritesPresenter {

    private FavoriteView mFavoriteView;

    public FavoritesPresenterImpl(FavoriteView mFavoriteView){
        this.mFavoriteView = mFavoriteView;
    }

    @Override
    public void loadList() {
        mFavoriteView.showProgress();
        mFavoriteView.setList(Listing.listAll(Listing.class));
        mFavoriteView.hideProgress();
    }

    @Override
    public void deleteItem(Listing item) {
        Listing.deleteAll(Listing.class, "listingid = ?", String.valueOf(item.getListing_id()));
        mFavoriteView.showMessage();
    }

    @Override
    public void returnItem(Listing item) {
        item.save();
    }

    @Override
    public void onDestroy() {
        mFavoriteView = null;
    }
}
