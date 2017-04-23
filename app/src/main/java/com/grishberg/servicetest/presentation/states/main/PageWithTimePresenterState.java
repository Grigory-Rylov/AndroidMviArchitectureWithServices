package com.grishberg.servicetest.presentation.states.main;

import com.github.mvpstatelib.framework.state.AbsMvpState;

import lombok.Getter;

/**
 * Created by grishberg on 01.04.17.
 */

public class PageWithTimePresenterState extends AbsMvpState {
    public static class SelectTimeButtonClicked extends PageWithTimePresenterState {
    }

    @Getter
    public static class TimeSelected extends PageWithTimePresenterState {
        private final int hour;
        private final int minutes;

        public TimeSelected(int hour, int minutes) {
            this.hour = hour;
            this.minutes = minutes;
        }
    }
}
