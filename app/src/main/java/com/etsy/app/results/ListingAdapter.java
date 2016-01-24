package com.etsy.app.results;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.app.R;
import com.etsy.app.network.models.Listing;
import com.etsy.app.network.models.MainImage;
import com.etsy.app.utils.Consts;
import com.etsy.app.utils.base.BaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ListingAdapter extends BaseRecyclerAdapter <Listing, ListingAdapter.ListingViewHolder> {

    private boolean isSearch;

    public ListingAdapter(List<Listing> list, boolean isSearch){
        super(list);
        this.isSearch = isSearch;
    }

    public static List<Listing> getAllFavorites(long id){
        Log.d(Consts.TAG, id+" ad");
        return Listing.find(Listing.class, "listingid = ?", String.valueOf(id));
    }

    @Override
    public ListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listing_item, parent, false);
        return new ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListingViewHolder holder, int position) {
        Listing listing = list.get(position);
        //String image = listing.getMainImage().getSmallImg();
        String image = listing.getSmall();

        holder.title.setText(listing.getTitle());
        holder.price.setText(String.format("%s %s", listing.getPrice(), listing.getCurrency_code()));

        List<Listing> favorites = getAllFavorites(listing.getListing_id());
        Log.d(Consts.TAG, favorites.size()+"");
        boolean isContains = favorites.size() > 0;
        listing.setFavorite(isContains);

        if (!isSearch){
            holder.action.setImageResource(R.drawable.ic_delete_black_24dp);
            holder.action.setColorFilter(Color.RED);
        } else if(listing.isFavorite()){
            holder.action.setImageResource(R.drawable.ic_star_black_24dp);
        } else {
            holder.action.setImageResource(R.drawable.ic_star_border_black_24dp);
        }

        Picasso.with(holder.itemView.getContext()).load(image).into(holder.image);
    }

    class ListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image, action;
        private TextView title, price;

        public ListingViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            action = (ImageView) itemView.findViewById(R.id.action);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);

            itemView.setOnClickListener(this);
            action.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ListingAdapter.this.itemClick(v, getAdapterPosition());
        }
    }
}