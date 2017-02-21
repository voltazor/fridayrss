package com.fridayrss.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fridayrss.interfaces.OnItemClickListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by voltazor on 26/01/17.
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final CopyOnWriteArrayList<T> list;
    private final Context context;
    private final LayoutInflater layoutInflater;
    @Nullable
    protected final OnItemClickListener<T> itemClickListener;

    public BaseAdapter(Context context) {
        this(context, null, null);
    }

    public BaseAdapter(Context context, List<T> list) {
        this(context, list, null);
    }

    public BaseAdapter(Context context, @Nullable OnItemClickListener<T> listener) {
        this(context, null, listener);
    }

    public BaseAdapter(Context context, List<T> list, @Nullable OnItemClickListener<T> listener) {
        layoutInflater = LayoutInflater.from(context);
        itemClickListener = listener;
        this.list = (list == null) ? new CopyOnWriteArrayList<T>() : new CopyOnWriteArrayList<T>(list);
        this.context = context;
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public T getItem(int position) {
        return position < 0 || position >= list.size() ? null : list.get(position);
    }

    @Override
    public final void onBindViewHolder(final VH holder, int position) {
        onBindViewHolder(holder, getItem(holder.getAdapterPosition()), position);
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    itemClickListener.onItemClick(getItem(position), holder.itemView, position);
                }
            });
        }
    }

    public List<T> getList() {
        return list;
    }

    public void addItem(T item) {
        if (list.add(item)) {
            notifyDataSetChanged();
        }
    }

    public void addItem(T item, int position) {
        list.add(position, item);
        notifyDataSetChanged();
    }

    public void setList(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void removeItem(T item) {
        if (list.remove(item)) {
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        if (list.remove(position) != null) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    protected Context getContext() {
        return context;
    }

    protected String getString(@StringRes int strResId) {
        return getContext().getString(strResId);
    }

    protected String getString(@StringRes int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    protected LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public abstract void onBindViewHolder(VH holder, T item, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

}
