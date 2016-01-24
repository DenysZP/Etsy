package com.etsy.app.results;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.app.R;
import com.etsy.app.details.DetailsFragment;
import com.etsy.app.main.MainActivity;
import com.etsy.app.network.models.Goods;
import com.etsy.app.network.models.Listing;
import com.etsy.app.results.presenter.ResultsPresenter;
import com.etsy.app.results.presenter.ResultsPresenterImpl;
import com.etsy.app.results.view.ResultsView;
import com.etsy.app.utils.Consts;
import com.etsy.app.utils.interfaces.OnItemClickListener;
import com.etsy.app.utils.interfaces.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment implements OnItemClickListener, ResultsView,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String GOODS = "goods";
    private static final String CATEGORY = "category";
    private static final String QUERY = "query";

    private View mView;
    private List<Listing> mResults;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecycler;
    private GridLayoutManager mLayoutManager;
    private ListingAdapter mRecyclerAdapter;
    private ResultsPresenter mPresenter;
    private boolean isBlocked;
    private boolean isLoading;
    private int mCurrentPage;
    private String query;

    public static ResultsFragment newInstance(Goods goods, String category, String query) {

        Bundle args = new Bundle();
        args.putParcelable(GOODS, goods);
        args.putString(CATEGORY, category);
        args.putString(QUERY, query);
        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.results_fragment, container, false);
        init();
        initToolbar();
        initRecycler();
        addScrollListener();

        return mView;
    }

    private void init(){

        Goods goods = getArguments().getParcelable(GOODS);
        mResults = new ArrayList<>();
        mResults.addAll(goods.getResults());
        String category = getArguments().getString(CATEGORY);
        query = getArguments().getString(QUERY);
        mPresenter = new ResultsPresenterImpl(this, category, query);
        mCurrentPage = 1;
        mRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.refresh);
        mRefresh.setOnRefreshListener(this);
    }

    private void initToolbar(){

        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.search_f, query));
        toolbar.setTitleTextColor(Color.WHITE);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        setHasOptionsMenu(true);
    }

    private void initRecycler(){

        mRecycler = (RecyclerView) mView.findViewById(R.id.recycler);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerAdapter = new ListingAdapter(mResults, true);
        mRecyclerAdapter.setItemClickListener(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mRecyclerAdapter);
    }

    private void addScrollListener(){
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int size = mLayoutManager.getItemCount();
                int last_position = mLayoutManager.findLastVisibleItemPosition();
                if (size == last_position + 1) {
                    if (!isLoading || isBlocked)
                        mPresenter.onItemsLoading(++mCurrentPage);
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == -1) return;

        Listing item = mResults.get(position);

        switch (view.getId()){
            case -1:
                ((Transaction)getActivity()).loadFragment(DetailsFragment.newInstance(item), true);
                break;
            case R.id.action:
                Log.d(Consts.TAG, item.isFavorite()+"");
                mPresenter.onAddItemToFavorite(item, item.isFavorite());
                mRecyclerAdapter.notifyItemChanged(position);
                break;
        }
    }

    @Override
    public void showProgress() {
        mRefresh.setRefreshing(true);
        isLoading = true;
    }

    @Override
    public void hideProgress() {
        mRefresh.setRefreshing(false);
        isLoading = false;
    }

    @Override
    public void setItems(List<Listing> items) {
        mResults.addAll(items);
        mRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void blocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        mResults.clear();
        isBlocked = isLoading = false;
        mPresenter.onItemsLoading(mCurrentPage);
        mRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().onBackPressed();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
