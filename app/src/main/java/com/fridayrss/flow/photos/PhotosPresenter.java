package com.fridayrss.flow.photos;

import android.support.annotation.Nullable;

import com.fridayrss.R;
import com.fridayrss.api.GeneralErrorHandler;
import com.fridayrss.api.body.ErrorBody;
import com.fridayrss.base.BaseMvpPresenterImpl;
import com.fridayrss.model.Photo;
import com.fridayrss.flow.photos.data.PhotosRepository;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by voltazor on 20/03/16.
 */
public class PhotosPresenter extends BaseMvpPresenterImpl<PhotosContract.View> implements PhotosContract.Presenter {

    @Nullable
    private String fridayId;
    private PhotosRepository repository;

    @Override
    public void initialize(@Nullable String fridayId) {
        repository = new PhotosRepository();
        this.fridayId = fridayId;
        view.setProgressVisible(true);
        handle(fridayId == null ? repository.getLastFridayPhotos() : repository.getPhotos(fridayId));
    }

    @Override
    public void updatePhotos() {
        view.setProgressVisible(true);
        handle(fridayId == null ? repository.getLastFridayPhotos(true) : repository.getPhotos(fridayId, true));
    }

    private void handle(Observable<List<Photo>> observable) {
        addSubscription(observable.subscribe(new Action1<List<Photo>>() {
            @Override
            public void call(List<Photo> photos) {
                view.setProgressVisible(false);
                view.setEmptyViewVisible(false);
                view.showPhotos(photos);
            }
        }, new GeneralErrorHandler(view, new GeneralErrorHandler.FailureCallback() {
            @Override
            public void onFailure(Throwable throwable, @Nullable ErrorBody errorBody, boolean isNetwork) {
                view.setProgressVisible(false);
                view.setEmptyViewVisible(true);
                view.showError(R.string.loading_rss_failed);
            }
        })));
    }

}
