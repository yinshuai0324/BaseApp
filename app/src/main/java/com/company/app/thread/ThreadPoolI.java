package com.company.app.thread;

import java.util.concurrent.Future;

/**
 * Created by yinshuai on 2017/11/13.
 */
public interface  ThreadPoolI {


    /**
     * 提交任务，获取任务的执行情况
     * @param task
     * @return
     */
    public Future<?> submit(Runnable task);
    /**
     * 执行任务，不关心任务的执行情况
     * @param task
     */
    public void execute(Runnable task);

    /**
     * 反射执行任务,不带参数
     * @param obj
     * @param methodName
     */
    public void execute(Object obj, String methodName);

    /**
     * 提交反射任务，不带参数
     * @param obj
     * @param methodName
     * @return
     */
    public Future<?> submit(Object obj, String methodName);

    /**
     * 停止任务
     * @param future
     * @return
     */
    public boolean stopTask(Future<?> future);
}
