package com.fridayrss.base;

/**
 * Created by voltazor on 20/03/16.
 */
public interface IBasePresenter<V extends IBaseMvpView> {

    <T extends V> void attachView(T view);

    void detachView();

}
