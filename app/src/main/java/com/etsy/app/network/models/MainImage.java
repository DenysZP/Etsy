package com.etsy.app.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class MainImage extends SugarRecord implements Parcelable {

    private String url_170x135;
    private String url_fullxfull;

    Listing listing;

    public MainImage(String smallImg, String fullImg) {
        this.url_170x135 = smallImg;
        this.url_fullxfull = fullImg;
    }

    public MainImage(){}

    protected MainImage(Parcel in) {
        url_170x135 = in.readString();
        url_fullxfull = in.readString();
    }

    public static final Creator<MainImage> CREATOR = new Creator<MainImage>() {
        @Override
        public MainImage createFromParcel(Parcel in) {
            return new MainImage(in);
        }

        @Override
        public MainImage[] newArray(int size) {
            return new MainImage[size];
        }
    };

    public String getSmallImg() {
        return url_170x135;
    }

    public void setSmallImg(String smallImg) {
        this.url_170x135 = smallImg;
    }

    public String getFullImg() {
        return url_fullxfull;
    }

    public void setFullImg(String fullImg) {
        this.url_fullxfull = fullImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url_170x135);
        dest.writeString(url_fullxfull);
    }
}
