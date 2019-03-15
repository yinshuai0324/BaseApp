package com.company.app.thread;

/**
 * Created by yinshuai on 2017/11/13.
 *
 * @author yinshuai
 *         <p/>
 *         带有优先级的任务
 */
public abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {

    private int priority;

    public PriorityRunnable(int priority) {
        if (priority < 0) {
            throw new IllegalArgumentException();
        }
        this.priority = priority;
    }

    public PriorityRunnable() {
        this.priority = ThreadFactory.NORMAL;
    }

    @Override
    public int compareTo(PriorityRunnable another) {
        int my = this.getPriority();
        int other = another.getPriority();
        return my < other ? 1 : my > other ? -1 : 0;
    }

    @Override
    public void run() {
        doSth();
    }

    public abstract void doSth();

    public int getPriority() {
        return priority;
    }
}
