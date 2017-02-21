package com.fridayrss.flow.photos.data;

import com.fridayrss.model.Photo;

import java.util.List;

import rx.Observable;

/**
 * Created by voltazor on 12/02/17.
 */
interface PhotosDataSource {

    Observable<List<Photo>> getLastFridayPhotos();

    Observable<List<Photo>> getPhotos(String fridayId);

    void storePhotos(List<Photo> photos);

}
