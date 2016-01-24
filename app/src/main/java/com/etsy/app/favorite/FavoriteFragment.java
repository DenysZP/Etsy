package com.etsy.app.favorite;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.etsy.app.R;
import com.etsy.app.details.DetailsFragment;
import com.etsy.app.favorite.presenter.FavoritesPresenter;
import com.etsy.app.favorite.presenter.FavoritesPresenterImpl;
import com.etsy.app.favorite.view.FavoriteView;
import com.etsy.app.network.models.Listing;
import com.etsy.app.results.ListingAdapter;
import com.etsy.app.utils.Consts;
import com.etsy.app.utils.interfaces.OnItemClickListener;
import com.etsy.app.utils.interfaces.Transaction;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteView, OnItemClickListener {

    private View mView;
    private List<Listing> mFavorites;
    private Listing mUndo;
    private int mUndoPosition;
    private ListingAdapter mAdapter;
    private ProgressBar mProgress;
    private FavoritesPresenter mPresenter;
    private TextView mEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.favorite_fragment, container, false);

        mProgress = (ProgressBar) mView.findViewById(R.id.progress);
        mEmpty = (TextView) mView.findViewById(R.id.empty);

        mUndoPosition = -1;

        mPresenter = new FavoritesPresenterImpl(this);

        RecyclerView recycler = (RecyclerView) mView.findViewById(R.id.recycler);
        mFavorites = new ArrayList<>();
        mAdapter = new ListingAdapter(mFavorites, false);
        mAdapter.setItemClickListener(this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(layoutManager);

        mPresenter.loadList();

        return mView;
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage() {
        Snackbar.make(mView,  getString(R.string.delete_message), Snackbar.LENGTH_LONG)
                .setAction( getString(R.string.undo), v -> {
            if (mUndo != null && mUndoPosition >= 0){
                mPresenter.returnItem(mUndo);
                mAdapter.addItem(mUndoPosition, mUndo);
                mUndo = null;
                mUndoPosition = -1;
            }
        }).setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent)).show();
    }

    @Override
    public void setList(List<Listing> items) {
        if (items.size() > 0){
            mFavorites.addAll(items);
            mAdapter.notifyDataSetChanged();
            mEmpty.setVisibility(View.GONE);
        } else {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Listing item = mFavorites.get(position);
        if (item == null) return;
        switch (view.getId()){
            case -1:
                ((Transaction) getActivity()).loadFragment(DetailsFragment.newInstance(item), true);
                break;
            case R.id.action:
                mUndo = item;
                mUndoPosition = position;
                mPresenter.deleteItem(item);
                mAdapter.removeItem(position);
                break;
        }
    }
}
