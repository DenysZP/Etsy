package com.etsy.app.main;

import android.app.Application;

import com.orm.SugarContext;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MainClass.getInstance().init(getApplicationContext());
        SugarContext.init(getApplicationContext());
    }
}
