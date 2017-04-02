package com.grishberg.servicetest.common.di;

import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.grishberg.servicetest.App;
import com.grishberg.servicetest.common.LogCatLogger;
import com.grishberg.servicetest.data.service.BoundServiceProvider;
import com.grishberg.servicetest.domain.interactors.MainScreenInteractor;

import java.lang.ref.WeakReference;

/**
 * Created by grishberg on 01.04.17.
 */

public class MyServiceLocator {
    @Nullable
    private static WeakReference<MainScreenInteractor> mainScreenInteractorRef;
    @Nullable
    private static WeakReference<BoundServiceProvider> boundServiceProviderRef;

    private MyServiceLocator(){}

    @MainThread
    public static BoundServiceProvider provideBoundServiceProvider() {
        if (boundServiceProviderRef == null || boundServiceProviderRef.get() == null) {
            BoundServiceProvider provider = getServiceProvider();
            boundServiceProviderRef = new WeakReference<>(provider);
            return provider;
        }
        return boundServiceProviderRef.get();
    }

    @NonNull
    private static BoundServiceProvider getServiceProvider() {
        Context appContext = App.getInstance().getApplicationContext();
        return new BoundServiceProvider(appContext);
    }

    @MainThread
    public static MainScreenInteractor provideMainScreenInteractor() {
        if (mainScreenInteractorRef == null || mainScreenInteractorRef.get() == null) {
            MainScreenInteractor interactor = new MainScreenInteractor(provideBoundServiceProvider(),
                    new LogCatLogger());
            mainScreenInteractorRef = new WeakReference<>(interactor);
            return interactor;
        }
        return mainScreenInteractorRef.get();
    }
}
