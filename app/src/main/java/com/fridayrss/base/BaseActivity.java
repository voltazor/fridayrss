package com.fridayrss.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.trello.navi.component.support.NaviAppCompatActivity;
import com.fridayrss.Const;
import com.fridayrss.R;
import com.fridayrss.interfaces.BaseActivityCallback;
import com.fridayrss.general.dialog.ProgressDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by voltazor on 20/03/16.
 */
public abstract class BaseActivity extends NaviAppCompatActivity implements BaseActivityCallback {

    @Nullable
    @BindView(android.R.id.content)
    protected View rootView;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;

    private ProgressDialogFragment progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResourceId() != 0) {
            setContentView(getLayoutResourceId());
        }
        setupToolbar();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
    }

    @LayoutRes
    protected abstract int getLayoutResourceId();

    @CallSuper
    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void setTitle(@StringRes int titleResId) {
        setTitle(getString(titleResId));
    }

    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    protected void setToolbarColor(@ColorInt int color) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(color);
        }
    }

    protected void showBackButton() {
        if (getSupportActionBar() != null) {
            if (toolbar != null) {
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            }
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void hideBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    protected void setStatusBarColor(@ColorRes int color) {
        if (Const.isAtLeastL()) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, color));
        }
    }

    @Override
    public void showError(String message) {
        showMessage(message);
    }

    @Override
    public void showError(@StringRes int strResId) {
        showError(getString(strResId));
    }

    @Override
    public void showMessage(String message) {
        if (rootView != null) {
            Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int srtResId) {
        showMessage(getString(srtResId));
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialogFragment.newInstance();
        }
        if (!progressDialog.isAdded()) {
            progressDialog.show(getSupportFragmentManager());
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isAdded()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            IBinder token = view.getWindowToken();
            if (inputManager != null && token != null) {
                inputManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void setFragment(Fragment fragment) {
        setFragment(fragment, R.id.container, false);
    }

    protected void setFragment(Fragment fragment, int containerId) {
        setFragment(fragment, containerId, false);
    }

    protected void setFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        String backStateName = fragment.getClass().getName();
        boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && (getSupportFragmentManager().findFragmentByTag(backStateName) == null)) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(containerId, fragment, backStateName);
            if (addToBackStack) {
                transaction.addToBackStack(backStateName);
            }
            transaction.commit();
        }
    }

    public static final class EXTRA {

        public static final String PHOTOS = "photos";

        public static final String POSITION = "position";

        public static final String FRIDAY = "friday";
    }

}
