package com.grishberg.servicetest.common.scheduler;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by grishberg on 02.04.17.
 */

public class PriorityFutureWrapper<T> implements RunnableFuture<T> {
    private final RunnableFuture<T> runnable;
    private final int priority;

    public PriorityFutureWrapper(RunnableFuture<T> runnable, int priority) {
        this.runnable = runnable;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public void run() {
        runnable.run();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return runnable.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return runnable.isCancelled();
    }

    @Override
    public boolean isDone() {
        return runnable.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return runnable.get();
    }

    @Override
    public T get(long timeout, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return runnable.get(timeout, unit);
    }
}
