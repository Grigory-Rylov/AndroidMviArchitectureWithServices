package com.grishberg.servicetest.data.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by grishberg on 01.04.17.
 */

public class BoundServiceProvider {
    private boolean bound;
    private final Context appContext;
    @Nullable
    private MultithreadingService service;
    @Nullable
    private WeakReference<ServiceReadyListener> serviceReadyListenerRef;

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder binder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MultithreadingService.MyBinder serviceBinder = (MultithreadingService.MyBinder) binder;
            service = serviceBinder.getService();
            bound = true;
            notifyServiceReady();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
            if (serviceReadyListenerRef != null && serviceReadyListenerRef.get() != null) {
                serviceReadyListenerRef.get().onServiceDisconnected();
            }
        }
    };

    public BoundServiceProvider(Context appContext) {
        this.appContext = appContext;
    }

    public void setServiceReadyListener(@Nullable ServiceReadyListener serviceReadyListener) {
        this.serviceReadyListenerRef = new WeakReference<>(serviceReadyListener);
    }

    public void startService() {
        if (bound) {
            notifyServiceReady();
        } else {
            // Bind to LocalService
            Intent intent = new Intent(appContext, MultithreadingService.class);
            appContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public void stopService(){
        if(bound){
            appContext.unbindService(serviceConnection);
            bound = false;
        }
    }

    private void notifyServiceReady() {
        if (serviceReadyListenerRef != null && serviceReadyListenerRef.get() != null) {
            serviceReadyListenerRef.get().onServiceReady(service);
        }
    }

    public interface ServiceReadyListener {
        void onServiceReady(Service service);

        void onServiceDisconnected();
    }
}
