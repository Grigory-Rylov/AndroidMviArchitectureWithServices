package com.grishberg.servicetest.common.scheduler;

/**
 * Created by grishberg on 02.04.17.
 */

public interface PriorityRunnable extends Runnable {
    int getPriority();
}
