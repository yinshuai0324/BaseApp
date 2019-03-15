package com.company.app.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by yinshuai on 2017/11/13.
 */
public class SingleThreadPool extends AbstractThreadPool {
    private ExecutorService executorService;

    public SingleThreadPool(){
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public Future<?> submit(Runnable task) {
        return this.executorService.submit(task);
    }

    @Override
    public void execute(Runnable task) {
        this.executorService.execute(task);
    }
}
