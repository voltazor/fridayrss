package com.fridayrss.flow.archive;

import com.fridayrss.base.IBaseMvpView;
import com.fridayrss.base.IBasePresenter;
import com.fridayrss.model.Friday;

import java.util.List;

/**
 * Created by voltazor on 20/03/16.
 */
public final class ArchiveContract {

    public interface Presenter extends IBasePresenter<View> {

        void initialize();

        void requestArchive();

    }

    public interface View extends IBaseMvpView {

        void showFridays(List<Friday> fridays);

        void setEmptyViewVisible(boolean visible);

        void setProgressVisible(boolean visible);

    }

}
