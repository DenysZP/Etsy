package com.etsy.app.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Goods implements Parcelable {

    private List<Listing> results;
    private PageInfo pagination;

    public Goods(List<Listing> results, PageInfo pagination) {
        this.results = results;
        this.pagination = pagination;
    }

    public List<Listing> getResults() {
        return results;
    }

    public void setResults(List<Listing> results) {
        this.results = results;
    }

    public PageInfo getPagination() {
        return pagination;
    }

    public void setPagination(PageInfo pagination) {
        this.pagination = pagination;
    }

    protected Goods(Parcel in) {
        results = in.createTypedArrayList(Listing.CREATOR);
        pagination = in.readParcelable(PageInfo.class.getClassLoader());
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(results);
        dest.writeParcelable(pagination, flags);
    }
}
