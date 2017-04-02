package com.grishberg.servicetest.presentation.states.main;

import com.grishberg.mvpstatelibrary.framework.state.MvpState;

import java.util.Date;

import lombok.Getter;

/**
 * Created by grishberg on 01.04.17.
 */

public class MainScreenViewState implements MvpState {

    @Getter
    public static class TimeSelectedResponse extends MainScreenViewState {
        private final int hour;
        private final int minutes;

        public TimeSelectedResponse(int hour, int minutes) {
            this.hour = hour;
            this.minutes = minutes;
        }
    }

    @Getter
    public static class DateSelectedResponse extends MainScreenViewState {
        private final Date date;

        public DateSelectedResponse(Date date) {
            this.date = date;
        }
    }
}
