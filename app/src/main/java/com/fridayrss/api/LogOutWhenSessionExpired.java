package com.fridayrss.api;

import android.widget.Toast;

import com.fridayrss.App;
import com.fridayrss.api.body.ErrorBody;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by voltazor on 20/06/16.
 */
public class LogOutWhenSessionExpired implements Func1<Observable<? extends Throwable>, Observable<?>> {

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread()).flatMap(new Func1<Throwable, Observable<?>>() {

            @Override
            public Observable<?> call(final Throwable throwable) {
                if (throwable instanceof HttpException) {
                    ErrorBody errorBody = ErrorBody.parseError(((HttpException) throwable).response());
                    if (errorBody != null) {
                        final int code = errorBody.getCode();
                        if (code == ErrorBody.INVALID_TOKEN) {
                            return Observable.empty().observeOn(AndroidSchedulers.mainThread()).doOnCompleted(new Action0() {
                                @Override
                                public void call() {
//                                    Toast.makeText(App.getContext(), R.string.your_session_expired, Toast.LENGTH_SHORT).show();
                                    App.logOut();
                                }
                            });
                        }
                        return Observable.error(new ApiException(throwable, errorBody));
                    }
                }

                // If cannot be handled - pass the original error through.
                return Observable.error(throwable);
            }

        });
    }

}
