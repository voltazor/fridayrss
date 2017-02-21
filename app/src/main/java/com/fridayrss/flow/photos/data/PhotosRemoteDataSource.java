package com.fridayrss.flow.photos.data;

import com.fridayrss.App;
import com.fridayrss.manager.ApiManager;
import com.fridayrss.model.Photo;

import java.util.List;

import rx.Observable;

/**
 * Created by voltazor on 12/02/17.
 */
class PhotosRemoteDataSource implements PhotosDataSource {

    private ApiManager apiManager = App.getApiManager();

    @Override
    public Observable<List<Photo>> getLastFridayPhotos() {
        return apiManager.getLastFridayPhotos();
    }

    @Override
    public Observable<List<Photo>> getPhotos(String fridayId) {
        return apiManager.getPhotosByFridayId(fridayId);
    }

    @Override
    public void storePhotos(List<Photo> photos) {
        //ignored
    }

}
