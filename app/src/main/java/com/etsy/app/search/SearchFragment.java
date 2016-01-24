package com.etsy.app.search;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.etsy.app.R;

import com.etsy.app.network.models.Categories;
import com.etsy.app.network.models.Goods;
import com.etsy.app.results.ResultsFragment;
import com.etsy.app.search.presenter.SearchPresenter;
import com.etsy.app.search.presenter.SearchPresenterImpl;
import com.etsy.app.search.view.SearchView;
import com.etsy.app.utils.Consts;
import com.etsy.app.utils.interfaces.Transaction;

public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher, SearchView {

    private View view;
    private Categories mCategories;
    private String mSearchCategory;
    private String mQuery;
    private TextInputLayout mSearchInput;
    private ProgressBar mProgress;
    private SearchPresenter mPresenter;
    private boolean isSearching;

    public static SearchFragment newInstance(Categories categories) {

        Bundle args = new Bundle();
        args.putParcelable(Consts.CATEGORIES, categories);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.search_fragment, container, false);
        mCategories = getArguments().getParcelable(Consts.CATEGORIES);

        mPresenter = new SearchPresenterImpl(this);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
        Spinner categoriesMenu = (Spinner) view.findViewById(R.id.categoriesMenu);
        categoriesMenu.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, mCategories.getListOfNames()));
        categoriesMenu.setOnItemSelectedListener(this);

        mSearchInput = (TextInputLayout) view.findViewById(R.id.searchInput);
        final EditText searchEdt = (EditText) view.findViewById(R.id.searchEdt);
        searchEdt.addTextChangedListener(this);
        FloatingActionButton searchButton = (FloatingActionButton) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> {
            String query = searchEdt.getText().toString().trim();
            if (query.isEmpty())
                mSearchInput.setError(getString(R.string.empty_search));
            else {
                search(query);
            }
        });

        return view;
    }

    @Override
    public void showProgress() {
        isSearching = true;
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        isSearching = false;
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showResults(Goods goods) {
        ((Transaction) getActivity()).loadFragment(ResultsFragment.newInstance(goods, mSearchCategory, mQuery), true);
    }

    private void search(String query) {
        if (isSearching) return;
        mQuery = query;
        mPresenter.search(mSearchCategory, query);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSearchCategory = mCategories.getResults().get(position).getName();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str = s.toString().trim();
        if (!str.isEmpty())
            mSearchInput.setError(null);
        else
            mSearchInput.setError(getString(R.string.empty_search));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
}
