package com.grishberg.servicetest.common.scheduler;

import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.util.Log;
import android.util.SparseArray;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by grishberg on 02.04.17.
 */

public class ThreadPoolContainer implements TaskObserver {
    private static final String TAG = ThreadPoolContainer.class.getSimpleName();
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int MAX_CORE_POOL_SIZE = 5;
    private static final int BLOCKING_QUEUE_INITIAL_CAPACITY = 10;
    private volatile Map<String, SparseArray<FutureContainer>> taskQueue;
    private int taskId;
    private final Object monitor = new Object();

    private PriorityBlockingQueue<Runnable> runnableQueue;
    private final ThreadPoolExecutor executor;

    private ThreadPoolContainer(int corePoolSize) {
        runnableQueue = new PriorityBlockingQueue<>(BLOCKING_QUEUE_INITIAL_CAPACITY, PRIORITY_FUTURE_COMPARATOR);
        executor = new PriorityThreadPoolExecutor(corePoolSize, runnableQueue);
        taskQueue = new ConcurrentHashMap<>(NUMBER_OF_CORES);
    }

    public static ThreadPoolContainer makeWithFixedCoreSize(int corePoolSize) {
        return new ThreadPoolContainer(corePoolSize);
    }

    public static ThreadPoolContainer makeWithAutomaticCoreSize() {
        int calculatedCorePoolSize = NUMBER_OF_CORES > MAX_CORE_POOL_SIZE ? MAX_CORE_POOL_SIZE : NUMBER_OF_CORES;
        return new ThreadPoolContainer(calculatedCorePoolSize);
    }

    @MainThread
    public int startManagedTask(AbsTask task, String tag) {
        int id = taskId++;
        if (taskId == Integer.MAX_VALUE) {
            taskId = 0;
        }
        task.setTaskId(id);
        Future future = executor.submit(task);
        SparseArray<FutureContainer> queue = taskQueue.get(tag);
        if (queue == null) {
            queue = new SparseArray<>(NUMBER_OF_CORES);
            taskQueue.put(tag, queue);
        }
        queue.put(id, new FutureContainer(future, task));
        return id;
    }

    @WorkerThread
    @Override
    public void onTaskDone(String useCaseTag, int taskId, boolean isInterrupted) {
        SparseArray<FutureContainer> queue = taskQueue.get(useCaseTag);
        if (queue != null) {
            synchronized (monitor) {
                FutureContainer task = queue.get(taskId);
                if (task != null) {
                    if (isInterrupted) {
                        Log.d(TAG, "task was delayed");
                    } else {
                        queue.remove(taskId);
                    }
                }
            }
        }
    }

    @MainThread
    public synchronized void delayTaskQueue(String useCaseTag) {
        SparseArray<FutureContainer> queue = taskQueue.get(useCaseTag);
        if (queue != null) {
            synchronized (monitor) {
                for (int i = queue.size() - 1; i >= 0; i--) {
                    int key = queue.keyAt(i);
                    // get the object by the key.
                    FutureContainer task = queue.get(key);
                    if (hasNotFinishedTask(task)) {
                        removeFromQueueAndRelaunch(task);
                    }
                }
            }
        }
    }

    private void removeFromQueueAndRelaunch(FutureContainer task) {
        task.future.cancel(true);
        runnableQueue.remove(task.runnable);
        // и заново добавить в очередь
        task.future = executor.submit(task.runnable);
    }

    private boolean hasNotFinishedTask(FutureContainer task) {
        return task != null && !task.future.isCancelled() && !task.future.isDone();
    }

    @MainThread
    public void cancelAll() {
        executor.shutdown();
        executor.shutdownNow();
        synchronized (monitor) {
            Iterator<Map.Entry<String, SparseArray<FutureContainer>>> it = taskQueue.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, SparseArray<FutureContainer>> item = it.next();
                SparseArray<FutureContainer> queue = taskQueue.get(item.getKey());
                if (queue != null) {
                    deleteTasksFromQueue(queue);
                }
                it.remove();
            }
        }
    }

    private void deleteTasksFromQueue(SparseArray<FutureContainer> queue) {
        for (int i = queue.size() - 1; i >= 0; i--) {
            int key = queue.keyAt(i);
            // get the object by the key.
            FutureContainer task = queue.get(key);
            if (isTaskRunning(task)) {
                task.future.cancel(true);
            }
            queue.removeAt(i);
        }
    }

    private boolean isTaskRunning(FutureContainer task) {
        return task != null && !task.future.isCancelled();
    }

    private static class FutureContainer {
        Future future;
        PriorityRunnable runnable;

        FutureContainer(Future future, PriorityRunnable runnable) {
            this.future = future;
            this.runnable = runnable;
        }
    }

    private static Comparator<Runnable> PRIORITY_FUTURE_COMPARATOR = new Comparator<Runnable>() {
        @Override
        public int compare(Runnable o1, Runnable o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null) {
                return -1;
            } else if (o2 == null) {
                return 1;
            } else {
                int p1 = ((PriorityFutureWrapper<?>) o1).getPriority();
                int p2 = ((PriorityFutureWrapper<?>) o2).getPriority();

                return p1 > p2 ? 1 : (p1 == p2 ? 0 : -1);
            }
        }
    };
}
