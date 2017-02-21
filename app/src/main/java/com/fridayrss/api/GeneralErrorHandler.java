package com.fridayrss.api;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.fridayrss.R;
import com.fridayrss.api.body.ErrorBody;
import com.fridayrss.base.IBaseMvpView;

import java.lang.ref.WeakReference;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by voltazor on 17/06/16.
 */
public class GeneralErrorHandler implements Action1<Throwable> {

    private final WeakReference<IBaseMvpView> mViewReference;
    private final FailureCallback mFailureCallback;
    private final boolean mShowError;

    public GeneralErrorHandler() {
        this(null, false, null);
    }

    public GeneralErrorHandler(IBaseMvpView view) {
        this(view, false, null);
    }

    public GeneralErrorHandler(IBaseMvpView view, boolean showError) {
        this(view, showError, null);
    }

    public GeneralErrorHandler(IBaseMvpView view, FailureCallback failureCallback) {
        this(view, true, failureCallback);
    }

    public GeneralErrorHandler(IBaseMvpView view, boolean showError, FailureCallback failureCallback) {
        mViewReference = new WeakReference<>(view);
        mFailureCallback = failureCallback;
        mShowError = showError;
    }

    @Override
    public void call(Throwable throwable) {
        Timber.e(throwable, throwable.getMessage());
        boolean isNetwork = false;
        ErrorBody errorBody = null;
        if (isNetworkError(throwable)) {
            isNetwork = true;
            showErrorIfRequired(R.string.internet_connection_unavailable);
        } else if (throwable instanceof HttpException) {
            handleError(errorBody = ErrorBody.parseError(((HttpException) throwable).response()));
        } else if (throwable instanceof ApiException) {
            handleError(errorBody = ((ApiException) throwable).getErrorBody());
        }
        if (mFailureCallback != null) {
            mFailureCallback.onFailure(throwable, errorBody, isNetwork);
        }
    }

    private boolean isNetworkError(Throwable throwable) {
        return throwable instanceof SocketException ||
                throwable instanceof UnknownHostException ||
                throwable instanceof SocketTimeoutException;
    }

    private void handleError(@Nullable ErrorBody errorBody) {
        if (errorBody != null) {
            switch (errorBody.getCode()) {
                case ErrorBody.INVALID_CREDENTIALS:
//                showErrorIfRequired(R.string.invalid_credentials);
                    break;
                case ErrorBody.EMAIL_IS_TAKEN:
//                showErrorIfRequired(R.string.email_is_taken);
                    break;
            }
        }
    }

    private void showErrorIfRequired(@StringRes int strResId) {
        if (mShowError) {
            IBaseMvpView view = mViewReference.get();
            if (view != null) {
                view.showError(strResId);
            }
        }
    }

    private void showErrorIfRequired(String error) {
        if (mShowError && !TextUtils.isEmpty(error)) {
            IBaseMvpView view = mViewReference.get();
            if (view != null) {
                view.showError(error);
            }
        }
    }

    public interface FailureCallback {

        void onFailure(Throwable throwable, @Nullable ErrorBody errorBody, boolean isNetwork);

    }

}
