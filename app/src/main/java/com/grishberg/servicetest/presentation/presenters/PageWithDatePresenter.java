package com.grishberg.servicetest.presentation.presenters;

import com.github.mvpstatelib.framework.presenter.BaseMvpPresenter;
import com.github.mvpstatelib.framework.state.MvpState;
import com.github.mvpstatelib.state.annotations.SubscribeState;
import com.grishberg.servicetest.domain.interactors.MainScreenInteractor;
import com.grishberg.servicetest.presentation.states.main.PageWithTimePresenterState;
import com.grishberg.servicetest.presentation.states.main.PageWithDateViewState;

/**
 * Created by grishberg on 01.04.17.
 */

public class PageWithDatePresenter extends BaseMvpPresenter {

    private final MainScreenInteractor interactor;

    public PageWithDatePresenter(MainScreenInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    protected void onStateUpdated(MvpState state) {
        GeneratedPageWithDatePresenterSubscriber.processState(this, state);
    }

    @SubscribeState
    void onShowDialog(PageWithDateViewState.ShowDateDialog state){
        updateViewState(new PageWithDateViewState.ShowDateDialog());
    }

    @SubscribeState
    void onTimeSelected(PageWithTimePresenterState.TimeSelected state) {
        interactor.onTimeSelected(state.getHour(), state.getMinutes());
        updateViewState(null);
    }
}
