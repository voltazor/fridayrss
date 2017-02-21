package com.fridayrss.general.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.viewpagerindicator.PageIndicator;

/**
 * Created by voltazor on 12/02/17.
 */
public class NumberPageIndicator extends TextView implements PageIndicator {

    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private int currentPage;
    private int scrollState;

    public NumberPageIndicator(Context context) {
        this(context, null);
    }

    public NumberPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NumberPageIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void calculate() {
        StringBuilder builder = new StringBuilder();
        builder.append(currentPage + 1);
        if (viewPager != null && viewPager.getAdapter() != null) {
            builder.append("/").append(viewPager.getAdapter().getCount());
        }
        setText(builder.toString());
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (viewPager == view) {
            return;
        }
        if (view.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        viewPager = view;
        viewPager.addOnPageChangeListener(this);
        calculate();
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (viewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        viewPager.setCurrentItem(item);
        currentPage = item;
        calculate();
    }

    @Override
    public void notifyDataSetChanged() {
        calculate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        scrollState = state;

        if (pageChangeListener != null) {
            pageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentPage = position;
        calculate();

        if (pageChangeListener != null) {
            pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
            currentPage = position;
            calculate();
        }

        if (pageChangeListener != null) {
            pageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        pageChangeListener = listener;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        NumberPageIndicator.SavedState savedState = (NumberPageIndicator.SavedState)state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPage = savedState.currentPage;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        NumberPageIndicator.SavedState savedState = new NumberPageIndicator.SavedState(superState);
        savedState.currentPage = currentPage;
        return savedState;
    }

    static class SavedState extends View.BaseSavedState {
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }

        @SuppressWarnings("UnusedDeclaration")
        public static final Parcelable.Creator<NumberPageIndicator.SavedState> CREATOR = new Parcelable.Creator<NumberPageIndicator.SavedState>() {
            @Override
            public NumberPageIndicator.SavedState createFromParcel(Parcel in) {
                return new NumberPageIndicator.SavedState(in);
            }

            @Override
            public NumberPageIndicator.SavedState[] newArray(int size) {
                return new NumberPageIndicator.SavedState[size];
            }
        };
    }

}
