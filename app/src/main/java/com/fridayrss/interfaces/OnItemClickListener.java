package com.fridayrss.interfaces;

import android.view.View;

/**
 * Created by voltazor on 26/01/17.
 */
public interface OnItemClickListener<T> {

    void onItemClick(T item, View view, int position);

}
