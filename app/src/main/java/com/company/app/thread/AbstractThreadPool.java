package com.company.app.thread;

import java.lang.reflect.Method;
import java.util.concurrent.Future;

/**
 * Created by yinshuai on 2017/11/13.
 *
 * @author yinshuai
 */
public abstract class AbstractThreadPool implements ThreadPoolI {

    /**
     * 反射执行任务,不带参数
     * @param obj
     * @param methodName
     */
    @Override
    public void execute(Object obj, String methodName) {
        execute(constructRunnable(obj, methodName));
    }


    /**
     * 提交反射任务，不带参数
     * @param obj
     * @param methodName
     * @return
     */
    @Override
    public Future<?> submit(Object obj, String methodName) {
        return submit(constructRunnable(obj, methodName));
    }



    /**
     * 停止任务
     * @param future
     * @return
     */
    @Override
    public boolean stopTask(Future<?> future) {
        if (future.isCancelled()) {
            return true;
        } else {
            return future.cancel(true);
        }
    }

    /**
     * 构造Runnable
     * @param obj
     * @param methodName
     * @return
     */
    private Runnable constructRunnable(final Object obj, final String methodName) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Method m = obj.getClass().getMethod(methodName);
                    m.invoke(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        return task;
    }

}
