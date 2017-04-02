package com.grishberg.servicetest.data.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by grishberg on 01.04.17.
 */

public class MultithreadingService extends Service {
    private static final String TAG = MultithreadingService.class.getSimpleName();
    final MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {
        MultithreadingService getService() {
            return MultithreadingService.this;
        }
    }
}
