package com.fridayrss.flow.photos;

import android.support.annotation.Nullable;

import com.fridayrss.base.IBaseMvpView;
import com.fridayrss.base.IBasePresenter;
import com.fridayrss.model.Photo;

import java.util.List;

/**
 * Created by voltazor on 20/03/16.
 */
public final class PhotosContract {

    public interface Presenter extends IBasePresenter<View> {

        void initialize(@Nullable String fridayId);

        void updatePhotos();

    }

    public interface View extends IBaseMvpView {

        void showPhotos(List<Photo> rss);

        void setEmptyViewVisible(boolean visible);

        void setProgressVisible(boolean visible);

    }

}
