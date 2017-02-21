package com.fridayrss.flow.photos.data;

import com.fridayrss.App;
import com.fridayrss.manager.DBManager;
import com.fridayrss.model.Photo;

import java.util.List;

import rx.Observable;

/**
 * Created by voltazor on 12/02/17.
 */
class PhotosLocalDataSource implements PhotosDataSource {

    private DBManager dbManager = App.getDBManager();

    @Override
    public Observable<List<Photo>> getLastFridayPhotos() {
        return dbManager.getPhotos();
    }

    @Override
    public Observable<List<Photo>> getPhotos(String fridayId) {
        return dbManager.getPhotosByFridayId(fridayId);
    }

    @Override
    public void storePhotos(List<Photo> photos) {
        dbManager.storePhotos(photos);
    }

}
