package com.fridayrss.flow.archive;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fridayrss.R;
import com.fridayrss.base.BaseAdapter;
import com.fridayrss.interfaces.OnItemClickListener;
import com.fridayrss.model.Friday;
import com.fridayrss.flow.photos.FridayActivity;
import com.fridayrss.general.fragment.BaseContentListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by voltazor on 30/10/16.
 */
public class ArchiveFragment extends BaseContentListFragment implements ArchiveContract.View {

    private ArchiveContract.Presenter presenter;
    private FridaysAdapter photosAdapter;

    public static ArchiveFragment newInstance() {
        return new ArchiveFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addPresenter(presenter = new ArchivePresenter());
        presenter.initialize();
    }

    @Override
    protected void requestContent() {
        presenter.requestArchive();
    }

    @Override
    public void showFridays(List<Friday> fridays) {
        setRefreshing(false);
        if (photosAdapter == null) {
            photosAdapter = new FridaysAdapter(getContext(), fridays, fridaysClickListener);
            setAdapter(photosAdapter);
        } else {
            photosAdapter.setList(fridays);
        }
    }

    private OnItemClickListener<Friday> fridaysClickListener = new OnItemClickListener<Friday>() {

        @Override
        public void onItemClick(Friday item, View view, int position) {
            startActivity(FridayActivity.newIntent(getContext(), item));
        }
    };

    @Override
    public String getFragmentTag() {
        return ArchiveFragment.class.getSimpleName();
    }

    static class FridaysAdapter extends BaseAdapter<Friday, FridaysAdapter.ViewHolder> {

        FridaysAdapter(Context context, List<Friday> fridays, @NonNull OnItemClickListener<Friday> listener) {
            super(context, fridays, listener);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getLayoutInflater().inflate(R.layout.item_friday, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, Friday item, int position) {
            holder.photo.setImageURI(item.getThumb());
            holder.text.setText(item.getName());
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.photo)
            SimpleDraweeView photo;

            @BindView(R.id.text)
            TextView text;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

        }

    }

}
