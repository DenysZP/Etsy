package com.etsy.app.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.app.R;
import com.etsy.app.details.presenter.DetailsPresenter;
import com.etsy.app.details.presenter.DetailsPresenterImpl;
import com.etsy.app.details.view.DetailsView;
import com.etsy.app.main.MainActivity;
import com.etsy.app.network.models.Listing;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment implements DetailsView {

    private static final String LISTING = "listing";
    private FloatingActionButton mFavoriteButton;
    private boolean isFavorite;
    private View mView;
    private DetailsPresenter mPresenter;
    private Listing mListing;

    public static DetailsFragment newInstance(Listing listing) {

        Bundle args = new Bundle();
        args.putParcelable(LISTING, listing);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.details_fragment, container, false);

        mPresenter = new DetailsPresenterImpl(this);
        mListing = getArguments().getParcelable(LISTING);

        isFavorite = !mListing.isFavorite();

        ImageView image = (ImageView) mView.findViewById(R.id.image);
        TextView title = (TextView) mView.findViewById(R.id.title);
        TextView price = (TextView) mView.findViewById(R.id.price);
        TextView description = (TextView) mView.findViewById(R.id.description);

        Picasso.with(getContext()).load(mListing.getFull()).into(image);
        title.setText(mListing.getTitle());
        price.setText(String.format("%s %s", mListing.getPrice(), mListing.getCurrency_code()));
        description.setText(mListing.getDescription());

        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.details));
        toolbar.setTitleTextColor(Color.WHITE);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        setHasOptionsMenu(true);

        mFavoriteButton = (FloatingActionButton) mView.findViewById(R.id.favoriteButton);
        changeFABState();
        mFavoriteButton.setOnClickListener((v) -> {

            if (isFavorite)
                mPresenter.deleteFromDB(mListing);
            else
                mPresenter.addToDB(mListing);


        } );

        return mView;
    }

    @Override
    public void changeFABState() {
        isFavorite = !isFavorite;
        mFavoriteButton.setImageResource(isFavorite
                ? R.drawable.ic_star_black_24dp
                : R.drawable.ic_star_border_black_24dp);
    }

    @Override
    public void showMessage(boolean isDeleted) {
        if (isDeleted)
            Snackbar.make(mView, getString(R.string.delete_message), Snackbar.LENGTH_LONG).show();
        else
            Snackbar.make(mView, getString(R.string.add_message), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().onBackPressed();
        return true;
    }
}
