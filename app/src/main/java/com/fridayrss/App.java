package com.fridayrss;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fridayrss.manager.ApiManager;
import com.fridayrss.manager.DBManager;
import com.fridayrss.manager.SharedPrefManager;

import timber.log.Timber;

/**
 * Created by voltazor on 20/03/16.
 */
public class App extends Application {

    private static final ApiManager apiManager = new ApiManager();
    private static final DBManager dbManager = new DBManager();
    private static final SharedPrefManager spManager = new SharedPrefManager();

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        Fresco.initialize(this);
        initManagers();
    }

    private void initLogger() {
        if (Const.DEBUG) {
            Timber.plant(new Timber.DebugTree());
//        } else {
//            Fabric.with(this, new Crashlytics());
        }
    }

    private void initManagers() {
        spManager.init(this);
        dbManager.init(this);
        apiManager.init(this);
    }

    public static ApiManager getApiManager() {
        return apiManager;
    }

    public static DBManager getDBManager() {
        return dbManager;
    }

    public static SharedPrefManager getSpManager() {
        return spManager;
    }

    public static void logOut() {
        //TODO
    }

}
