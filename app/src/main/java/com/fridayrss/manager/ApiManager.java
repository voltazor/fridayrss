package com.fridayrss.manager;

import android.content.Context;

import com.fridayrss.Const;
import com.fridayrss.api.ApiSettings;
import com.fridayrss.api.LogOutWhenSessionExpired;
import com.fridayrss.api.deserializer.StringDeserializer;
import com.fridayrss.api.services.NetworkService;
import com.fridayrss.interfaces.Manager;
import com.fridayrss.model.Friday;
import com.fridayrss.model.Photo;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by voltazor on 20/03/16.
 */
public class ApiManager implements Manager {

    private NetworkService networkService;

    @Override
    public void init(Context context) {
        initNetworkServices(createRetrofit(createOkHttpClient()));
    }

    private void initNetworkServices(Retrofit retrofit) {
        networkService = retrofit.create(NetworkService.class);
    }

    private Retrofit createRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(createGsonConverter())
                .baseUrl(ApiSettings.SERVER)
                .client(client)
                .build();
    }

    private GsonConverterFactory createGsonConverter() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.registerTypeAdapter(String.class, new StringDeserializer());
        builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        return GsonConverterFactory.create(builder.create());
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.connectTimeout(60, TimeUnit.SECONDS);
        if (Const.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

    public Observable<List<Friday>> getFridays() {
        return networkService.getFridaysList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new LogOutWhenSessionExpired())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Photo>> getPhotosByFridayId(String fridayId) {
        return getPhotos(networkService.getPhotosByFridayId(fridayId));
    }

    public Observable<List<Photo>> getLastFridayPhotos() {
        return getPhotos(networkService.getLastFridayPhotos());
    }

    private Observable<List<Photo>> getPhotos(Observable<List<Photo>> observable) {
        return observable.subscribeOn(Schedulers.io())
                .map(new Func1<List<Photo>, List<Photo>>() {
                    @Override
                    public List<Photo> call(List<Photo> photos) {
                        Collections.sort(photos);
                        return photos;
                    }
                })
                .retryWhen(new LogOutWhenSessionExpired())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void clear() {

    }

}
