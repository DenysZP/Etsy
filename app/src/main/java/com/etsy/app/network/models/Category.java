package com.etsy.app.network.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {

    private long category_id;
    private String name;
    private String short_name;

    public Category(long category_id, String name, String short_name) {
        this.category_id = category_id;
        this.name = name;
        this.short_name = short_name;
    }

    protected Category(Parcel in) {
        category_id = in.readLong();
        name = in.readString();
        short_name = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(category_id);
        dest.writeString(name);
        dest.writeString(short_name);
    }
}
