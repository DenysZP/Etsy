package com.etsy.app.main;

import android.content.Context;
import android.util.Log;

import com.etsy.app.network.EtsyAPI;
import com.etsy.app.utils.Consts;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class MainClass {

    private static MainClass instance = null;
    private Context context;

    private MainClass(){}

    public void init(Context context){
        this.context = context.getApplicationContext();
    }

    public static MainClass getInstance() {
        if (instance == null) {
            instance = new MainClass ();
        }
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public EtsyAPI getApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(EtsyAPI.class);
    }
}
