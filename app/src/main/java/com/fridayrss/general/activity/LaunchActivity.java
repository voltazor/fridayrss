package com.fridayrss.general.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fridayrss.base.BaseActivity;

public class LaunchActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, LaunchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(MainActivity.newIntent(this));
    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

}
