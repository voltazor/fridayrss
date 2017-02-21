package com.fridayrss.general.activity;

import android.content.Context;
import android.content.Intent;

import com.fridayrss.App;
import com.fridayrss.R;
import com.fridayrss.base.BaseActivity;

import butterknife.OnClick;

public class WarningActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, WarningActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_warning;
    }

    @OnClick(R.id.accept)
    void accept() {
        App.getSpManager().setContentAccepted(true);
        startActivity(MainActivity.newIntent(this));
        finishAffinity();
    }

    @OnClick(R.id.close)
    void close() {
        finishAffinity();
    }

}
