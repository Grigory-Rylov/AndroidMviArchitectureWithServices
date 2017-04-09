package com.grishberg.servicetest.common.scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by grishberg on 02.04.17.
 */

public class PriorityThreadPoolExecutor extends ThreadPoolExecutor {

    public PriorityThreadPoolExecutor(int corePoolSize,  BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, corePoolSize, 0, TimeUnit.MILLISECONDS, workQueue);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        RunnableFuture<T> newTaskFor = super.newTaskFor(runnable, value);
        return new PriorityFutureWrapper<>(newTaskFor, ((PriorityRunnable) runnable).getPriority());
    }

    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable) {
        RunnableFuture<T> newTaskFor = super.newTaskFor(runnable, null);
        return new PriorityFutureWrapper<>(newTaskFor, ((PriorityRunnable) runnable).getPriority());
    }
}
