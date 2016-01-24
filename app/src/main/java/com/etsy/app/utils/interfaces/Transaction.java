package com.etsy.app.utils.interfaces;

import android.support.v4.app.Fragment;

public interface Transaction {
    void loadFragment(Fragment fragment, boolean isBackStackAllowed);
}
