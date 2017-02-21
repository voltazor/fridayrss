package com.fridayrss.flow.photos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.fridayrss.R;
import com.fridayrss.base.BaseActivity;
import com.fridayrss.model.Friday;

public class FridayActivity extends BaseActivity {

    public static Intent newIntent(Context context, @NonNull Friday friday) {
        return new Intent(context, FridayActivity.class).putExtra(EXTRA.FRIDAY, friday);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBackButton();
        Friday friday = getIntent().getParcelableExtra(EXTRA.FRIDAY);
        setTitle(friday.getName());
        setFragment(PhotosListFragment.newInstance(friday.getId()));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_friday;
    }

}
