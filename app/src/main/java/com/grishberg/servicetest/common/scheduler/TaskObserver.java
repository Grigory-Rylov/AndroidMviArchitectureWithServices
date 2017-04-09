package com.grishberg.servicetest.common.scheduler;

/**
 * Created by grishberg on 02.04.17.
 */

public interface TaskObserver {
    void onTaskDone(String useCaseTag, int taskId, boolean isInterrupted);
}
