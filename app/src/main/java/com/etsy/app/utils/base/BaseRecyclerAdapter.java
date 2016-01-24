package com.etsy.app.utils.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.app.utils.interfaces.OnItemClickListener;

import java.util.List;

public abstract class BaseRecyclerAdapter <M, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected OnItemClickListener itemClickListener;

    protected List<M> list;

    public BaseRecyclerAdapter() {}

    public BaseRecyclerAdapter(List<M> list) {
        this.list = list;
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    public void itemClick(View view, int position){
        if (itemClickListener != null)
            itemClickListener.onItemClick(view, position);
    }

    public M removeItem(int position) {
        final M model = list.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(M model) {
        list.add(model);
        notifyItemInserted(list.size());
    }

    public void addItem(int position, M model) {
        list.add(position, model);
        notifyItemInserted(position);
    }

    public void updateItem(int position, M model){
        if (list.size() > position){
            list.set(position, model);
            notifyItemChanged(position);
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        final M model = list.remove(fromPosition);
        list.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void callItemClickListener(View v, int position) {
        if (itemClickListener != null && position != -1)
            itemClickListener.onItemClick(v, position);
    }

    public void setList(List<M> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

}