package com.etsy.app.search.presenter;

import com.etsy.app.main.MainClass;
import com.etsy.app.search.view.SearchView;
import com.etsy.app.utils.Consts;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {

    private SearchView mSearchView;

    public SearchPresenterImpl(SearchView mSearchView){
        this.mSearchView = mSearchView;
    }

    @Override
    public void search(String category, String query) {
        mSearchView.showProgress();
        MainClass.getInstance().getApiService().getAllGoodsPerPage(
                Consts.API_KEY,
                category,
                query, String.valueOf(1))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goods -> {

                    mSearchView.hideProgress();

                    if (goods == null || goods.getResults() == null)
                        mSearchView.showMessage("Something Went Wrong");
                    else if (goods.getResults().size() > 0)
                        mSearchView.showResults(goods);
                    else
                        mSearchView.showMessage("No results");

                });
    }

    @Override
    public void onDestroy() {
        mSearchView = null;
    }
}
