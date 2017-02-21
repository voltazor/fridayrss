package com.fridayrss.interfaces;

import android.support.annotation.StringRes;

/**
 * Created by Dmitriy-Dovbnya on 02.09.2015.
 */
public interface BaseActivityCallback {

    void setTitle(@StringRes int strResId);

    void setTitle(String title);

    void showError(@StringRes int strResId);

    void showError(String message);

    void hideKeyboard();

    void showMessage(String message);

    void showMessage(@StringRes int srtResId);

    void showProgress();

    void hideProgress();

}
