package com.fridayrss.api;

import com.fridayrss.api.body.ErrorBody;

/**
 * Created by voltazor on 30/10/16.
 */
public class ApiException extends Exception {

    private final ErrorBody mErrorBody;

    public ApiException(Throwable cause, ErrorBody errorBody) {
        super(cause);
        mErrorBody = errorBody;
    }

    public ErrorBody getErrorBody() {
        return mErrorBody;
    }

}
