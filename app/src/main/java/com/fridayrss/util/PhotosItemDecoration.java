package com.fridayrss.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fridayrss.R;

/**
 * Created by voltazor on 20/03/16.
 */
public class PhotosItemDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public PhotosItemDecoration(@NonNull Context context) {
        mItemOffset = context.getResources().getDimensionPixelSize(R.dimen.margin_small);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, 0);
        int position = parent.getChildLayoutPosition(view);
        if (position == 0 || position == 1) {
            outRect.top = mItemOffset;
        }
        if (position % 2 == 0) {
            outRect.left = mItemOffset;
            outRect.right = mItemOffset / 2;
        } else {
            outRect.left = mItemOffset / 2;
            outRect.right = mItemOffset;
        }
        outRect.bottom = mItemOffset;
    }

}
