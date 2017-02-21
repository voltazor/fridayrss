package com.fridayrss.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.navi.component.support.NaviFragment;
import com.fridayrss.interfaces.BaseActivityCallback;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends NaviFragment {

    private Unbinder unbinder;
    private BaseActivityCallback baseActivityCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            baseActivityCallback = (BaseActivityCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.getClass().getSimpleName() + " must implement" + BaseActivityCallback.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getLayoutResourceId() > 0) {
            View view = inflater.inflate(getLayoutResourceId(), container, false);
            unbinder = ButterKnife.bind(this, view);
            return view;
        } else {
            return null;
        }
    }

    protected abstract int getLayoutResourceId();

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseActivityCallback = null;
    }


    public void setTitle(@StringRes int srtResId) {
        baseActivityCallback.setTitle(srtResId);
    }

    public void setTitle(String title) {
        baseActivityCallback.setTitle(title);
    }

    public void showProgress() {
        baseActivityCallback.showProgress();
    }

    public void hideProgress() {
        baseActivityCallback.hideProgress();
    }

    public void showError(String message) {
        baseActivityCallback.showError(message);
    }

    public void showError(@StringRes int strResId) {
        baseActivityCallback.showError(strResId);
    }

    public void hideKeyboard() {
        baseActivityCallback.hideKeyboard();
    }

    public void showMessage(String message) {
        baseActivityCallback.showMessage(message);
    }

    public void showMessage(@StringRes int srtResId) {
        baseActivityCallback.showMessage(srtResId);
    }

    public abstract String getFragmentTag();

}
