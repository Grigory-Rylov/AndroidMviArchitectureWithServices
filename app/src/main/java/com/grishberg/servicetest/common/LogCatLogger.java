package com.grishberg.servicetest.common;

import android.util.Log;

/**
 * Created by grishberg on 01.04.17.
 */

public class LogCatLogger implements Logger {
    @Override
    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }
}
