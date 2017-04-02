package com.grishberg.servicetest.presentation.presenters;

import com.grishberg.mvpstatelibrary.framework.presenter.BaseMvpPresenter;
import com.grishberg.mvpstatelibrary.framework.state.MvpState;
import com.grishberg.servicetest.domain.interactors.MainScreenInteractor;
import com.grishberg.servicetest.presentation.states.main.PageViewState;

/**
 * Created by grishberg on 01.04.17.
 */

public class PagePresenter extends BaseMvpPresenter<PageViewState> {

    private final MainScreenInteractor interactor;

    public PagePresenter(MainScreenInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    protected void onStateUpdated(MvpState presenterState) {

    }
}
