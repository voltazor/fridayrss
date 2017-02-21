package com.fridayrss.flow.photos.data;

import com.fridayrss.model.Photo;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by voltazor on 12/02/17.
 */
public class PhotosRepository {

    private PhotosDataSource photosLocalDataSource;
    private PhotosDataSource photosRemoteDataSource;

    public PhotosRepository() {
        this.photosLocalDataSource = new PhotosLocalDataSource();
        this.photosRemoteDataSource = new PhotosRemoteDataSource();
    }

    public Observable<List<Photo>> getLastFridayPhotos() {
        return getLastFridayPhotos(false);
    }

    public Observable<List<Photo>> getLastFridayPhotos(boolean refresh) {
        if (refresh) {
            return photosRemoteDataSource.getLastFridayPhotos().doOnNext(new Action1<List<Photo>>() {
                @Override
                public void call(List<Photo> photos) {
                    photosLocalDataSource.storePhotos(photos);
                }
            });
        } else {
            return photosLocalDataSource.getLastFridayPhotos().flatMap(new Func1<List<Photo>, Observable<List<Photo>>>() {
                @Override
                public Observable<List<Photo>> call(List<Photo> photos) {
                    if (photos == null || photos.isEmpty()) {
                        return getLastFridayPhotos(true);
                    }
                    return Observable.just(photos);
                }
            }).onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Photo>>>() {
                @Override
                public Observable<? extends List<Photo>> call(Throwable throwable) {
                    return getLastFridayPhotos(true);
                }
            });
        }
    }

    public Observable<List<Photo>> getPhotos(String fridayId) {
        return getPhotos(fridayId, false);
    }

    public Observable<List<Photo>> getPhotos(final String fridayId, boolean refresh) {
        if (refresh) {
            return photosRemoteDataSource.getPhotos(fridayId).doOnNext(new Action1<List<Photo>>() {
                @Override
                public void call(List<Photo> photos) {
                    photosLocalDataSource.storePhotos(photos);
                }
            });
        } else {
            return photosLocalDataSource.getPhotos(fridayId).flatMap(new Func1<List<Photo>, Observable<List<Photo>>>() {
                @Override
                public Observable<List<Photo>> call(List<Photo> photos) {
                    if (photos == null || photos.isEmpty()) {
                        return getPhotos(fridayId, true);
                    }
                    return Observable.just(photos);
                }
            }).onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Photo>>>() {
                @Override
                public Observable<? extends List<Photo>> call(Throwable throwable) {
                    return getLastFridayPhotos(true);
                }
            });
        }
    }


}
