package com.etsy.app.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class Listing extends SugarRecord implements Parcelable {

    private long listing_id;
    private String title;
    private String description;
    private double price;
    private String currency_code;
    private MainImage MainImage;
    private boolean isFavorite;
    private String small, full;

    public Listing(){}

    public Listing(long listing_id, String title, String description, double price, String currency_code, MainImage mainImage) {
        this.listing_id = listing_id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.currency_code = currency_code;
        MainImage = mainImage;
    }

    public String getSmall() {
        if (small == null) {
            full = MainImage.getFullImg();
            small = MainImage.getSmallImg();
            return small;
        } else
            return small;
    }

    public String getFull() {
        if (full == null) {
            full = MainImage.getFullImg();
            small = MainImage.getSmallImg();
            return full;
        } else
            return full;
    }

    public long getListing_id() {
        return listing_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public MainImage getMainImage() {
        return MainImage;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;

        if (obj != null && obj instanceof Listing)
        {
            isEquals = this.listing_id == ((Listing) obj).listing_id;
        }

        return isEquals;
    }

    protected Listing(Parcel in) {
        listing_id = in.readLong();
        title = in.readString();
        description = in.readString();
        price = in.readDouble();
        currency_code = in.readString();
        MainImage = in.readParcelable(MainImage.class.getClassLoader());
    }

    public static final Creator<Listing> CREATOR = new Creator<Listing>() {
        @Override
        public Listing createFromParcel(Parcel in) {
            return new Listing(in);
        }

        @Override
        public Listing[] newArray(int size) {
            return new Listing[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(listing_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeString(currency_code);
        dest.writeParcelable(MainImage, flags);
    }
}
