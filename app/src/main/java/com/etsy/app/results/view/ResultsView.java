package com.etsy.app.results.view;

import com.etsy.app.network.models.Listing;

import java.util.List;

public interface ResultsView {
    void showProgress();
    void hideProgress();
    void setItems(List<Listing> items);
    void blocked(boolean isBlocked);
}
