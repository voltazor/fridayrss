package com.fridayrss.flow.archive.data;

import com.fridayrss.App;
import com.fridayrss.manager.ApiManager;
import com.fridayrss.model.Friday;

import java.util.List;

import rx.Observable;

/**
 * Created by voltazor on 12/02/17.
 */
class ArchiveRemoteDataSource implements ArchiveDataSource {

    private ApiManager apiManager = App.getApiManager();

    @Override
    public Observable<List<Friday>> getFridays() {
        return apiManager.getFridays();
    }

    @Override
    public void storeFridays(List<Friday> fridays) {
        //ignored
    }

}
