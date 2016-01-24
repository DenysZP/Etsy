package com.etsy.app.details.presenter;

import com.etsy.app.network.models.Listing;

public interface DetailsPresenter {

    void addToDB(Listing item);

    void deleteFromDB(Listing item);

    void onDestroy();
}
