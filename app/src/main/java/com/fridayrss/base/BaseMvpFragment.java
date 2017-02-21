package com.fridayrss.base;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMvpFragment extends BaseFragment implements IBaseMvpView {

    protected List<IBasePresenter> presenters = new ArrayList<>();

    protected void addPresenter(IBasePresenter presenter) {
        if (presenter != null) {
            presenter.attachView(this);
            presenters.add(presenter);
        }
    }

    @Override
    public void onDestroyView() {
        for (IBasePresenter presenter : presenters) {
            if (presenter != null) {
                presenter.detachView();
            }
        }
        super.onDestroyView();
    }

}
