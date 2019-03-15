package com.company.app.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yinshuai on 2017/11/13.
 *
 * @author yinshuai
 */
public class ScheduledThreadPool {
    private ScheduledExecutorService scheduledThreadPool;

    private Map<String, Future> futureMap = new HashMap<>();

    public ScheduledThreadPool(int scheduledPoolSize) {
        scheduledThreadPool = Executors.newScheduledThreadPool(scheduledPoolSize);
    }


    /**
     * 默认的循环执行,1秒执行一次
     *
     * @param runnable
     */
    public void defaultExecuteCycle(Runnable runnable) {
        Future future = this.scheduledThreadPool.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.MILLISECONDS);
        futureMap.put(runnable.getClass().getName(), future);
    }


    /**
     * 循环执行
     *
     * @param runnable
     */
    public void executeCycle(Runnable runnable, int delay, int period) {
        Future future = this.scheduledThreadPool.scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);
        futureMap.put(runnable.getClass().getName(), future);
    }


    /**
     * 循环执行
     *
     * @param runnable
     */
    public void executeCycle(Runnable runnable, int delay, int period, String tag) {
        if (isRunningInPool(tag)) {
            return;
        }
        Future future = this.scheduledThreadPool.scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);
        futureMap.put(tag, future);
    }


    /**
     * 延迟执行
     *
     * @param runnable
     * @param delay
     */
    public void executeDelay(Runnable runnable, int delay) {
        this.scheduledThreadPool.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }


    /**
     * 是否在线程池中执行
     *
     * @param tag
     * @return
     */
    public boolean isRunningInPool(String tag) {
        if (futureMap.get(tag) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 移除任务 但是任务还在执行
     *
     * @param tag
     */
    public void stopTask(String tag) {
        Future future = this.futureMap.get(tag);
        if (future != null) {
            future.cancel(true);
            futureMap.remove(tag);
        }
    }

    /**
     * 移除任务 会自动尝试终止任务
     */
    public void shutDown() {
        if (!scheduledThreadPool.isShutdown()) {
            this.scheduledThreadPool.shutdown();
        }
    }
}
