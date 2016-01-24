package com.etsy.app.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.app.R;
import com.etsy.app.favorite.FavoriteFragment;
import com.etsy.app.network.models.Categories;
import com.etsy.app.search.SearchFragment;
import com.etsy.app.utils.Consts;
import com.etsy.app.utils.base.BaseViewPagerAdapter;

public class MainFragment extends Fragment {

    public static MainFragment newInstance(Categories categories) {

        Bundle args = new Bundle();
        args.putParcelable(Consts.CATEGORIES, categories);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);

        Categories categories = getArguments().getParcelable(Consts.CATEGORIES);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

        ViewPager mContainer = (ViewPager) view.findViewById(R.id.pager);
        BaseViewPagerAdapter adapter = new BaseViewPagerAdapter(getFragmentManager());
        adapter.addFragment(SearchFragment.newInstance(categories), getString(R.string.search));
        adapter.addFragment(new FavoriteFragment(), getString(R.string.favorite));
        mContainer.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mContainer);

        return view;
    }
}
