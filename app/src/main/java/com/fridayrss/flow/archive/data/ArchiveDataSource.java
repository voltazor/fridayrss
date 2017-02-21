package com.fridayrss.flow.archive.data;

import com.fridayrss.model.Friday;

import java.util.List;

import rx.Observable;

/**
 * Created by voltazor on 12/02/17.
 */

interface ArchiveDataSource {

    Observable<List<Friday>> getFridays();

    void storeFridays(List<Friday> fridays);

}
