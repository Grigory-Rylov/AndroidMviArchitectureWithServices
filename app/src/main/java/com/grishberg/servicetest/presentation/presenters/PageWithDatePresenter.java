package com.grishberg.servicetest.presentation.presenters;

import com.grishberg.mvpstatelibrary.framework.presenter.BaseMvpPresenter;
import com.grishberg.mvpstatelibrary.framework.state.MvpState;
import com.grishberg.servicetest.domain.interactors.MainScreenInteractor;
import com.grishberg.servicetest.presentation.states.main.PageWithTimePresenterState;
import com.grishberg.servicetest.presentation.states.main.PageWithDateViewState;

/**
 * Created by grishberg on 01.04.17.
 */

public class PageWithDatePresenter extends BaseMvpPresenter<PageWithDateViewState> {

    private final MainScreenInteractor interactor;

    public PageWithDatePresenter(MainScreenInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    protected void onStateUpdated(MvpState state) {
        if (state instanceof PageWithTimePresenterState.SelectTimeButtonClicked) {
            updateViewState(new PageWithDateViewState.ShowDateDialog());
        } else if (state instanceof PageWithTimePresenterState.TimeSelected) {
            onTimeSelected((PageWithTimePresenterState.TimeSelected) state);
        }
    }

    private void onTimeSelected(PageWithTimePresenterState.TimeSelected state) {
        interactor.onTimeSelected(state.getHour(), state.getMinutes());
        updateViewState(null);
    }
}
