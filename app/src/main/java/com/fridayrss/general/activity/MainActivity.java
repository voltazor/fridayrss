package com.fridayrss.general.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.fridayrss.R;
import com.fridayrss.base.BaseActivity;
import com.fridayrss.flow.archive.ArchiveFragment;
import com.fridayrss.flow.photos.PhotosListFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements DrawerLayout.DrawerListener {

    private static final int FRIDAY = 0;
    private static final int ARCHIVE = 1;
    private static final int SETTINGS = 2;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle drawerToggle;

    private int page = -1;
    private int showPage;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, getToolbar(), R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(this);
        showPage(FRIDAY);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.friday)
    void openFriday() {
        openPage(FRIDAY);
    }

    @OnClick(R.id.archive)
    void openArchive() {
        openPage(ARCHIVE);
    }

    @OnClick(R.id.settings)
    void openSettings() {
        openPage(SETTINGS);
    }

    @OnClick(R.id.admin_panel)
    void openAdminPanel() {
//        showToast("Not implemented yet");
    }

    private void openPage(int showPage) {
        this.showPage = showPage;
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void showPage(int showPage) {
        if (showPage != page) {
            this.page = showPage;
            switch (page) {
                case FRIDAY:
                    setTitle(R.string.last_category);
                    setFragment(PhotosListFragment.newInstance());
                    break;
                case ARCHIVE:
                    setTitle(R.string.archive);
                    setFragment(ArchiveFragment.newInstance());
                    break;
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        drawerToggle.onDrawerSlide(drawerView, slideOffset);
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        drawerToggle.onDrawerOpened(drawerView);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        drawerToggle.onDrawerClosed(drawerView);
        showPage(showPage);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        drawerToggle.onDrawerStateChanged(newState);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else if (page != FRIDAY) {
            showPage(FRIDAY);
        } else {
            super.onBackPressed();
        }
    }

}
