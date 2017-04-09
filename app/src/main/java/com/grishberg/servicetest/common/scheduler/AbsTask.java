package com.grishberg.servicetest.common.scheduler;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by grishberg on 02.04.17.
 */

public abstract class AbsTask implements PriorityRunnable {
    private final TaskObserver taskObserver;
    private final String useCaseTag;
    @Getter private final int priority;
    @Getter @Setter private int taskId;

    public AbsTask(TaskObserver taskObserver, String useCaseTag, int priority) {
        this.taskObserver = taskObserver;
        this.useCaseTag = useCaseTag;
        this.priority = priority;
    }

    @Override
    public void run() {
        boolean isInterrupted = false;
        try {
            runTask();
        } catch (InterruptedException e) {
            isInterrupted = true;
        }
        onDone(isInterrupted);
    }

    protected void onDone(boolean isInterrupted) {
        taskObserver.onTaskDone(useCaseTag, taskId, isInterrupted);
    }

    protected abstract void runTask() throws InterruptedException;
}
