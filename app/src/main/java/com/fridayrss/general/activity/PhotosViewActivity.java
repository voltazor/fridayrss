package com.fridayrss.general.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

import com.fridayrss.R;
import com.fridayrss.base.BaseActivity;
import com.fridayrss.interfaces.FocusableView;
import com.fridayrss.interfaces.ViewPagerProvider;
import com.fridayrss.model.Photo;
import com.fridayrss.flow.photos.PhotoFragment;
import com.fridayrss.general.widget.NumberPageIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class PhotosViewActivity extends BaseActivity implements ViewPagerProvider {

    @BindView(R.id.indicator)
    NumberPageIndicator pageIndicator;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private int currentPage;
    private ImagePagerAdapter pagerAdapter;

    public static Intent newIntent(Context context, @NonNull Photo photo) {
        return newIntent(context, Collections.singletonList(photo));
    }

    public static Intent newIntent(Context context, @NonNull List<Photo> photos) {
        return newIntent(context, photos, 0);
    }

    public static Intent newIntent(Context context, @NonNull List<Photo> photos, int position) {
        Intent intent = new Intent(context, PhotosViewActivity.class);
        intent.putParcelableArrayListExtra(EXTRA.PHOTOS, new ArrayList<Parcelable>(photos));
        intent.putExtra(EXTRA.POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Photo> photos = getIntent().getParcelableArrayListExtra(EXTRA.PHOTOS);
        pagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), this, photos);
        viewPager.setAdapter(pagerAdapter);
        if (photos.size() > 1) {
            pageIndicator.setViewPager(viewPager);
            pageIndicator.setVisibility(View.VISIBLE);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (position != currentPage) {
                        setViewInFocus(currentPage, false);
                        setViewInFocus(currentPage = position, true);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            viewPager.setCurrentItem(getIntent().getIntExtra(EXTRA.POSITION, 0));
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_photos_view;
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    private void setViewInFocus(int position, boolean inFocus) {
        if (pagerAdapter != null) {
            FocusableView focusableView = pagerAdapter.getFocusableView(position);
            if (focusableView != null) {
                focusableView.setInFocus(inFocus);
            }
        }
    }

    private class ImagePagerAdapter extends FragmentPagerAdapter {

        private final List<Photo> photos;
        private ViewPagerProvider viewPagerProvider;
        private SparseArray<FocusableView> focusableViews;

        ImagePagerAdapter(FragmentManager fm, @NonNull ViewPagerProvider provider, List<Photo> photos) {
            super(fm);
            this.photos = photos;
            this.viewPagerProvider = provider;
            focusableViews = new SparseArray<>(photos.size());
        }

        @Override
        public Fragment getItem(int position) {
            PhotoFragment fragment = PhotoFragment.newInstance(photos.get(position));
            focusableViews.put(position, fragment);
            if (viewPagerProvider.getViewPager() != null && position == viewPagerProvider.getViewPager().getCurrentItem()) {
                fragment.setInFocus(true);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Nullable
        FocusableView getFocusableView(int position) {
            return focusableViews.get(position);
        }

    }

}
