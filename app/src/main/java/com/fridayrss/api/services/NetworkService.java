package com.fridayrss.api.services;

import com.fridayrss.model.Friday;
import com.fridayrss.model.Photo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


import static com.fridayrss.api.ApiSettings.*;

/**
 * Created by voltazor on 01/07/16.
 */
public interface NetworkService {

    @GET(LAST_FRIDAY_PHOTOS)
    Observable<List<Photo>> getLastFridayPhotos();

    @GET(PHOTOS_BY_FRIDAY_ID)
    Observable<List<Photo>> getPhotosByFridayId(@Path(FRIDAY_ID) String fridayId);

    @GET(FRIDAYS_LIST)
    Observable<List<Friday>> getFridaysList();

}
