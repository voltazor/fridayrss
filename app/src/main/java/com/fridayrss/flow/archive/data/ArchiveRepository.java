package com.fridayrss.flow.archive.data;

import com.fridayrss.model.Friday;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by voltazor on 12/02/17.
 */
public class ArchiveRepository {

    private ArchiveDataSource localDataSource;
    private ArchiveDataSource remoteDataSource;

    public ArchiveRepository() {
        this.localDataSource = new ArchiveLocalDataSource();
        this.remoteDataSource = new ArchiveRemoteDataSource();
    }

    public Observable<List<Friday>> getFridays() {
        return getFridays(false);
    }

    public Observable<List<Friday>> getFridays(boolean refresh) {
        if (refresh) {
            return remoteDataSource.getFridays().doOnNext(new Action1<List<Friday>>() {
                @Override
                public void call(List<Friday> fridays) {
                    localDataSource.storeFridays(fridays);
                }
            });
        } else {
            return localDataSource.getFridays().flatMap(new Func1<List<Friday>, Observable<List<Friday>>>() {
                @Override
                public Observable<List<Friday>> call(List<Friday> fridays) {
                    if (fridays == null || fridays.isEmpty()) {
                        return getFridays(true);
                    }
                    return Observable.just(fridays);
                }
            }).onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Friday>>>() {
                @Override
                public Observable<? extends List<Friday>> call(Throwable throwable) {
                    return getFridays(true);
                }
            });
        }
    }

}
