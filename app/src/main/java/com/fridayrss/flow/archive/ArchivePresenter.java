package com.fridayrss.flow.archive;

import android.support.annotation.Nullable;

import com.fridayrss.R;
import com.fridayrss.api.GeneralErrorHandler;
import com.fridayrss.api.body.ErrorBody;
import com.fridayrss.flow.archive.data.ArchiveRepository;
import com.fridayrss.base.BaseMvpPresenterImpl;
import com.fridayrss.model.Friday;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by voltazor on 20/03/16.
 */
public class ArchivePresenter extends BaseMvpPresenterImpl<ArchiveContract.View> implements ArchiveContract.Presenter {

    private ArchiveRepository repository;

    @Override
    public void initialize() {
        repository = new ArchiveRepository();
        view.setProgressVisible(true);
        handle(repository.getFridays());
    }

    private void handle(Observable<List<Friday>> observable) {
        addSubscription(observable.subscribe(new Action1<List<Friday>>() {
            @Override
            public void call(List<Friday> fridays) {
                showFridays(fridays);
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

    @Override
    public void requestArchive() {
        view.setProgressVisible(true);
        handle(repository.getFridays(true));
    }

    private void showFridays(List<Friday> fridays) {
        view.setProgressVisible(false);
        view.setEmptyViewVisible(false);
        view.showFridays(fridays);
    }

}
