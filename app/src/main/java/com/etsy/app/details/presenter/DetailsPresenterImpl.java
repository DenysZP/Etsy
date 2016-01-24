package com.etsy.app.details.presenter;

import com.etsy.app.details.view.DetailsView;
import com.etsy.app.network.models.Listing;

public class DetailsPresenterImpl implements DetailsPresenter {

    private DetailsView mDetailsView;

    public DetailsPresenterImpl(DetailsView mDetailsView) {
        this.mDetailsView = mDetailsView;
    }

    @Override
    public void addToDB(Listing item) {
        item.save();
        mDetailsView.showMessage(false);
        mDetailsView.changeFABState();
    }

    @Override
    public void deleteFromDB(Listing item) {
        Listing.deleteAll(Listing.class, "listingid = ?", String.valueOf(item.getListing_id()));
        mDetailsView.showMessage(true);
        mDetailsView.changeFABState();
    }

    @Override
    public void onDestroy() {
        mDetailsView = null;
    }
}
