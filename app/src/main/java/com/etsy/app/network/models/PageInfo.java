package com.etsy.app.network.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PageInfo implements Parcelable{

    private int effective_page;
    private int next_page;

    public PageInfo(int effective_page, int next_page) {
        this.effective_page = effective_page;
        this.next_page = next_page;
    }

    protected PageInfo(Parcel in) {
        effective_page = in.readInt();
        next_page = in.readInt();
    }

    public static final Creator<PageInfo> CREATOR = new Creator<PageInfo>() {
        @Override
        public PageInfo createFromParcel(Parcel in) {
            return new PageInfo(in);
        }

        @Override
        public PageInfo[] newArray(int size) {
            return new PageInfo[size];
        }
    };

    public int getEffective_page() {
        return effective_page;
    }

    public int getNext_page() {
        return next_page;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(effective_page);
        dest.writeInt(next_page);
    }
}
