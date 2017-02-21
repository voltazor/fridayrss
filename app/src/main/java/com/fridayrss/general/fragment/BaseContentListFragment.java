package com.fridayrss.general.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fridayrss.R;
import com.fridayrss.base.BaseAdapter;
import com.fridayrss.base.BaseMvpFragment;
import com.fridayrss.util.PhotosItemDecoration;
import com.fridayrss.general.widget.ProgressVector;

import butterknife.BindView;

/**
 * Created by voltazor on 30/10/16.
 */
public abstract class BaseContentListFragment extends BaseMvpFragment {

    @BindView(R.id.progress)
    ProgressVector progress;

    @BindView(R.id.content)
    RecyclerView contentRecyclerView;

    @BindView(R.id.empty_view)
    View emptyView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_content_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contentRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        contentRecyclerView.addItemDecoration(new PhotosItemDecoration(getContext()));
        contentRecyclerView.setHasFixedSize(true);

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestContent();
            }
        });
    }

    protected void setAdapter(BaseAdapter adapter) {
        contentRecyclerView.setAdapter(adapter);
    }

    protected void setRefreshing(final boolean refreshing) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(refreshing);
            }
        });
    }

    protected abstract void requestContent();

    public void setEmptyViewVisible(boolean visible) {
        emptyView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setProgressVisible(boolean visible) {
        if (visible) {
            progress.start();
        } else {
            progress.stop();
        }
    }

}
