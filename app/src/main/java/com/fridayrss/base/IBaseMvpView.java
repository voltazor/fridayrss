package com.fridayrss.base;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * Created by voltazor on 20/03/16.
 */
public interface IBaseMvpView {

    Context getContext();

    void showProgress();

    void hideProgress();

    void showError(@StringRes int strResId);

    void showError(String error);

    void showMessage(@StringRes int srtResId);

    void showMessage(String message);

}
