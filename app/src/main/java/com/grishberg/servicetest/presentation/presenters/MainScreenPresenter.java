package com.grishberg.servicetest.presentation.presenters;

import com.github.mvpstatelib.framework.presenter.BaseMvpPresenter;
import com.github.mvpstatelib.framework.state.MvpState;
import com.grishberg.servicetest.domain.interactors.MainScreenInteractor;
import com.grishberg.servicetest.presentation.states.main.MainScreenPresenterState;
import com.grishberg.servicetest.presentation.states.main.MainScreenViewState;
import com.grishberg.servicetest.presentation.states.main.MainScreenViewState.*;

/**
 * Created by grishberg on 01.04.17.
 */

public class MainScreenPresenter extends BaseMvpPresenter {

    private final MainScreenInteractor interactor;

    public MainScreenPresenter(MainScreenInteractor interactor) {
        this.interactor = interactor;
        interactor.observeMainScreenEvents(this);
    }

    @Override
    protected void onStateUpdated(MvpState state) {
        if (state instanceof MainScreenPresenterState.Request) {
            processRequest((MainScreenPresenterState.Request) state);
        } else if (state instanceof MainScreenPresenterState.TimeSelected) {
            processTimeSelected((MainScreenPresenterState.TimeSelected) state);
        }
    }

    private void processTimeSelected(MainScreenPresenterState.TimeSelected state) {
        updateViewState(new TimeSelectedResponse(state.getHour(), state.getMinutes()));
    }

    private void processRequest(MainScreenPresenterState.Request request) {

    }

    @Override
    public void onDestroy() {
        interactor.onMainScreenClosed();
    }
}
