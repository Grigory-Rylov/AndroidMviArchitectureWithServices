package com.grishberg.servicetest.domain.interactors;

import android.app.Service;
import android.support.annotation.Nullable;

import com.grishberg.mvpstatelibrary.framework.state.MvpState;
import com.grishberg.mvpstatelibrary.framework.state.StateReceiver;
import com.grishberg.servicetest.common.Logger;
import com.grishberg.servicetest.data.service.BoundServiceProvider;
import com.grishberg.servicetest.presentation.states.main.MainScreenPresenterState;

/**
 * Created by grishberg on 01.04.17.
 */

public class MainScreenInteractor {
    private static final String TAG = MainScreenInteractor.class.getSimpleName();
    private final BoundServiceProvider serviceProvider;
    @Nullable
    private StateReceiver<MvpState> mainScreenPresenter;
    private final Logger log;

    public MainScreenInteractor(BoundServiceProvider serviceProvider, Logger log) {
        this.serviceProvider = serviceProvider;
        this.serviceProvider.startService();
        this.log = log;
        serviceProvider.setServiceReadyListener(new BoundServiceProvider.ServiceReadyListener() {
            @Override
            public void onServiceReady(Service service) {
                MainScreenInteractor.this.log.d(TAG, "onServiceReady " + service);
            }

            @Override
            public void onServiceDisconnected() {
                MainScreenInteractor.this.log.d(TAG, "onServiceDisconnected");
            }
        });
    }

    public void observeMainScreenEvents(StateReceiver<MvpState> mainScreenPresenter) {
        this.mainScreenPresenter = mainScreenPresenter;
    }

    public void onTimeSelected(int hour, int minutes) {
        if (mainScreenPresenter != null) {
            mainScreenPresenter.updateState(new MainScreenPresenterState.TimeSelected(hour, minutes));
        }
    }

    public void onMainScreenClosed() {
        serviceProvider.stopService();
    }
}
