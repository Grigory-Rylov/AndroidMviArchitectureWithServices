package com.grishberg.servicetest;

import android.app.Application;

/**
 * Created by grishberg on 01.04.17.
 */

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        App.setInstance(this);
    }

    private static void setInstance(App instance) {
        App.instance = instance;
    }

    public static App getInstance() {
        return instance;
    }
}
