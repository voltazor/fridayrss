package com.fridayrss.flow.archive.data;

import com.fridayrss.App;
import com.fridayrss.manager.DBManager;
import com.fridayrss.model.Friday;

import java.util.List;

import rx.Observable;

/**
 * Created by voltazor on 12/02/17.
 */
class ArchiveLocalDataSource implements ArchiveDataSource {

    private DBManager dbManager = App.getDBManager();

    @Override
    public Observable<List<Friday>> getFridays() {
        return dbManager.getFridays();
    }

    @Override
    public void storeFridays(List<Friday> fridays) {
        dbManager.storeFridays(fridays);
    }

}
