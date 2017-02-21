package com.fridayrss.flow.photos;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fridayrss.Const;
import com.fridayrss.R;
import com.fridayrss.base.BaseAdapter;
import com.fridayrss.model.Photo;
import com.fridayrss.general.activity.PhotosViewActivity;
import com.fridayrss.general.fragment.BaseContentListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by voltazor on 30/10/16.
 */
public class PhotosListFragment extends BaseContentListFragment implements PhotosContract.View {

    private static final String FRIDAY_ID = "friday_id";

    private PhotosContract.Presenter presenter;
    private PhotosAdapter photosAdapter;

    public static PhotosListFragment newInstance() {
        return newInstance(null);
    }

    public static PhotosListFragment newInstance(@Nullable String fridayId) {
        PhotosListFragment fragment = new PhotosListFragment();
        Bundle args = new Bundle();
        args.putString(FRIDAY_ID, fridayId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addPresenter(presenter = new PhotosPresenter());
        presenter.initialize(getArguments() != null ? getArguments().getString(FRIDAY_ID) : null);
    }

    @Override
    protected void requestContent() {
        presenter.updatePhotos();
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        setRefreshing(false);
        if (photosAdapter == null) {
            photosAdapter = new PhotosAdapter(getContext(), photos, photosClickListener);
            setAdapter(photosAdapter);
        } else {
            photosAdapter.setList(photos);
        }
    }

    private PhotosClickListener photosClickListener = new PhotosClickListener() {
        @Override
        public void onPhotoClick(List<Photo> photos, View view, int position) {
            Intent intent = PhotosViewActivity.newIntent(getContext(), photos, position);
            if (Const.isAtLeastM()) {
                startActivity(intent, ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.getWidth(), view.getHeight()).toBundle());
            } else if (Const.isAtLeastL()) {
                startActivity(intent, ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight()).toBundle());
            } else {
                startActivity(intent);
            }
        }
    };

    @Override
    public String getFragmentTag() {
        return PhotosListFragment.class.getSimpleName();
    }

    static class PhotosAdapter extends BaseAdapter<Photo, PhotosAdapter.ViewHolder> {

        private PhotosClickListener listener;

        PhotosAdapter(Context context, List<Photo> photos, @NonNull PhotosClickListener listener) {
            super(context, photos);
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getLayoutInflater().inflate(R.layout.item_photo, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, Photo item, int position) {
            holder.photo.setImageURI(item.getThumb());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPhotoClick(getList(), holder.itemView, holder.getAdapterPosition());
                }
            });
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.photo)
            SimpleDraweeView photo;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

        }

    }

    private interface PhotosClickListener {

        void onPhotoClick(List<Photo> photos, View view, int position);

    }

}
