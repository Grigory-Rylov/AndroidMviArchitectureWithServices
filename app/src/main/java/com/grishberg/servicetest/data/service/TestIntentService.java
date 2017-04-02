package com.grishberg.servicetest.data.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by grishberg on 01.04.17.
 */

public class TestIntentService extends IntentService {

    public TestIntentService() {
        super(TestIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
