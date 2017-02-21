package com.fridayrss.flow.photos;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.fridayrss.R;
import com.fridayrss.base.BaseFragment;
import com.fridayrss.interfaces.FocusableView;
import com.fridayrss.interfaces.ViewPagerProvider;
import com.fridayrss.model.Photo;

import butterknife.BindView;

/**
 * Created by voltazor on 24/01/17.
 */
public class PhotoFragment extends BaseFragment implements FocusableView {

    private static final String PHOTO = "photo";

    @BindView(R.id.progress)
    View progressBar;

    @BindView(R.id.container)
    GestureFrameLayout containerView;

    @BindView(R.id.photo)
    SimpleDraweeView imageView;

    private boolean inFocus;
    private Animatable animatable;
    private ViewPagerProvider viewPagerProvider;

    public static PhotoFragment newInstance(Photo photo) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PHOTO, photo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            viewPagerProvider = (ViewPagerProvider) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.getClass().getSimpleName() + " should implement " + ViewPagerProvider.class.getSimpleName());
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_photo;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        attachViewPager();
        containerView.getController()
                .getSettings()
                .setMaxZoom(2f)
                .setZoomEnabled(true)
                .setOverzoomFactor(2f)
                .setDoubleTapEnabled(true);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                        progressBar.setVisibility(View.GONE);
                        animatable = anim;
                        if (anim != null && inFocus) {
                            anim.start();
                        }
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        progressBar.setVisibility(View.GONE);
                    }
                })
                .setUri(getPhotoUrl()).build();
        imageView.setController(controller);
    }

    private String getPhotoUrl() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(PHOTO)) {
            Photo photo = bundle.getParcelable(PHOTO);
            if (photo != null) {
                return photo.getUrl();
            }
        }
        return null;
    }

    @Override
    public void setInFocus(boolean inFocus) {
        this.inFocus = inFocus;
        if (animatable != null) {
            if (inFocus && !animatable.isRunning()) {
                animatable.start();
            } else if (animatable.isRunning()) {
                animatable.stop();
            }
        }
    }

    private void attachViewPager() {
        if (containerView != null && viewPagerProvider != null) {
            containerView.getController().enableScrollInViewPager(viewPagerProvider.getViewPager());
        }
    }

    private void detachViewPager() {
        if (containerView != null) {
            containerView.getController().disableViewPager(true);
        }
    }

    @Override
    public void onDetach() {
        detachViewPager();
        viewPagerProvider = null;
        super.onDetach();
    }

    @Override
    public String getFragmentTag() {
        return PhotoFragment.class.getSimpleName();
    }

}
