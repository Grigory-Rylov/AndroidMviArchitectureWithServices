package com.grishberg.servicetest.presentation.presenters;

import com.github.mvpstatelib.framework.presenter.BaseMvpPresenter;
import com.github.mvpstatelib.framework.state.MvpState;
import com.grishberg.servicetest.domain.interactors.MainScreenInteractor;

/**
 * Created by grishberg on 01.04.17.
 */

public class PagePresenter extends BaseMvpPresenter {

    private final MainScreenInteractor interactor;

    public PagePresenter(MainScreenInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    protected void onStateUpdated(MvpState presenterState) {

    }
}
