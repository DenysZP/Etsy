package com.etsy.app.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etsy.app.R;
import com.etsy.app.network.models.Categories;
import com.etsy.app.utils.Consts;
import com.etsy.app.utils.interfaces.Transaction;

public class MainActivity extends AppCompatActivity implements Transaction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Categories categories = getIntent().getParcelableExtra(Consts.CATEGORIES);
        loadFragment(MainFragment.newInstance(categories), false);
    }

    @Override
    public void loadFragment(Fragment fragment, boolean isBackStackAllowed) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        if (isBackStackAllowed)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
