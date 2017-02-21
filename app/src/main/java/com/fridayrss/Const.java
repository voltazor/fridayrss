package com.fridayrss;

import android.os.Build;

/**
 * Created by voltazor on 20/03/16.
 */
public class Const {

    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static boolean isAtLeastL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isAtLeastM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
