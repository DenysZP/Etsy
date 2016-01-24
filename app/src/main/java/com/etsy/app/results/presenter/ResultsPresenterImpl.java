package com.etsy.app.results.presenter;

import android.util.Log;

import com.etsy.app.main.MainClass;
import com.etsy.app.network.models.Goods;
import com.etsy.app.network.models.Listing;
import com.etsy.app.results.view.ResultsView;
import com.etsy.app.utils.Consts;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ResultsPresenterImpl implements ResultsPresenter {

    private ResultsView mResultsView;
    private String mCategory;
    private String mQuery;

    public ResultsPresenterImpl(ResultsView mResultsView, String category, String query){
        this.mResultsView = mResultsView;
        this.mCategory = category;
        this.mQuery = query;
    }

    @Override
    public void onItemsLoading(int page) {
        mResultsView.showProgress();
        MainClass.getInstance().getApiService()
                .getAllGoodsPerPage(Consts.API_KEY, mCategory, mQuery, String.valueOf(page))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Goods>() {
                    @Override
                    public void onCompleted() {
                        mResultsView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mResultsView.hideProgress();
                        mResultsView.blocked(true);
                    }

                    @Override
                    public void onNext(Goods goods) {
                        if (goods != null && goods.getResults() != null && goods.getResults().size() > 0)
                            mResultsView.setItems(goods.getResults());
                        else
                            mResultsView.blocked(true);
                    }
                });
    }

    @Override
    public void onAddItemToFavorite(Listing item, boolean isFavorite) {

        if (isFavorite) {
            Listing.deleteAll(Listing.class, "listingid = ?", String.valueOf(item.getListing_id()));
        }
        else {
            item.save();
        }
    }

    @Override
    public void onDestroy() {
        mResultsView = null;
    }

}
