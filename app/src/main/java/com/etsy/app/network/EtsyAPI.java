package com.etsy.app.network;

import com.etsy.app.network.models.Categories;
import com.etsy.app.network.models.Goods;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface EtsyAPI {

    @GET("taxonomy/categories?")
    Observable<Categories> getAllCategories(@Query("api_key") String apiKey);

    @GET("listings/active?includes=MainImage")
    Observable<Goods> getAllGoodsPerPage(
            @Query("api_key") String apiKey,
            @Query("category") String category,
            @Query("keywords") String keywords,
            @Query("page") String page);

}
