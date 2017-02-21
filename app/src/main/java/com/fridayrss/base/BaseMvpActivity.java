package com.fridayrss.base;

import android.content.Context;
import android.support.annotation.CallSuper;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMvpActivity extends BaseActivity implements IBaseMvpView {

    protected List<IBasePresenter> presenters = new ArrayList<>();

    protected void addPresenter(IBasePresenter presenter) {
        if (presenter != null) {
            presenter.attachView(this);
            presenters.add(presenter);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        for (IBasePresenter presenter : presenters) {
            if (presenter != null) {
                presenter.detachView();
            }
        }
        super.onDestroy();
    }

}
