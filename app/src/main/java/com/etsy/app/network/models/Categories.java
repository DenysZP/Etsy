package com.etsy.app.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Categories implements Parcelable {

    private int count;
    private List<Category> results;

    protected Categories(Parcel in) {
        count = in.readInt();
        results = new ArrayList<>();
        in.readList(results, Categories.class.getClassLoader());
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Category> getResults() {
        return results;
    }

    public void setResults(List<Category> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeList(results);
    }

    public List<String> getListOfNames(){
        List<String> list = new ArrayList<>();
        if (results != null)
            for (Category cat : results) {
                list.add(cat.getShort_name());
            }
        return list;
    }
}
