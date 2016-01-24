package com.etsy.app.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.etsy.app.R;
import com.etsy.app.main.MainActivity;
import com.etsy.app.main.MainClass;
import com.etsy.app.network.EtsyAPI;
import com.etsy.app.network.models.Categories;
import com.etsy.app.utils.Consts;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        EtsyAPI etsyAPI = MainClass.getInstance().getApiService();

        etsyAPI.getAllCategories(Consts.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Categories>() {
                    @Override
                    public void onCompleted() {
                        Log.d(Consts.TAG, "Complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Categories categories) {
                        Intent main = new Intent(SplashActivity.this, MainActivity.class);
                        main.putExtra(Consts.CATEGORIES, categories);
                        startActivity(main);
                        finish();
                    }
                });
    }
}
