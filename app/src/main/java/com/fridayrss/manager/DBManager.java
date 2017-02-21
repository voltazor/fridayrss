package com.fridayrss.manager;

import android.content.Context;

import com.fridayrss.interfaces.Manager;
import com.fridayrss.model.Friday;
import com.fridayrss.model.Photo;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by voltazor on 23/04/16.
 */
public class DBManager implements Manager {

    private static final String NAME = "fridayRss";
    private static final int SCHEMA_VERSION = 0;

    private Realm realm;

    @Override
    public void init(Context context) {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(context);
        builder.name(NAME).schemaVersion(SCHEMA_VERSION);
        builder.deleteRealmIfMigrationNeeded();
        realm = Realm.getInstance(builder.build());
    }

    public void storeFridays(final List<Friday> fridays) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(fridays);
            }
        });
    }

    public Observable<List<Friday>> getFridays() {
        return realm.where(Friday.class).findAllSorted(Friday.ID, Sort.DESCENDING).asObservable()
                .map(new Func1<RealmResults<Friday>, List<Friday>>() {
            @Override
            public List<Friday> call(RealmResults<Friday> fridays) {
                return fridays;
            }
        });
    }

    public void storePhotos(final List<Photo> photos) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(photos);
            }
        });
    }

    public Observable<Photo> getPhotoById(String photoId) {
        return realm.where(Photo.class).equalTo(Photo.ID, photoId).findFirst().asObservable();
    }

    public Observable<List<Photo>> getPhotosByFridayId(String fridayId) {
        return realm.where(Photo.class).equalTo(Photo.DB_FRIDAY_ID, fridayId).findAllSorted(Photo.ID, Sort.DESCENDING).asObservable()
                .map(new Func1<RealmResults<Photo>, List<Photo>>() {
                    @Override
                    public List<Photo> call(RealmResults<Photo> photos) {
                        return photos;
                    }
                });
    }

    public Observable<List<Photo>> getPhotos() {
        Photo photo = realm.where(Photo.class).findFirst();
        if (photo != null) {
            return getPhotosByFridayId(photo.getFridayId());
        }
        return Observable.just(null);
    }

    @Override
    public void clear() {
    }

}
