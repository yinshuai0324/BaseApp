package com.company.app.thread;

/**
 * Created by yinshuai on 2017/11/13.
 *
 * @author yinshuai
 */
public class ThreadFactory {
    public static final int HEIGHT = 10;
    public static final int NORMAL = 5;
    public static final int LOW = 0;


    /**
     * 通常核心线程数可以设为CPU数量+1，而最大线程数可以设为CPU的数量*2+1。
     * <p>
     * Runtime.getRuntime().availableProcessors()获取CPU核心数
     */
    public static int poolMinSize = Runtime.getRuntime().availableProcessors() + 1;
    public static int poolMaxSize = Runtime.getRuntime().availableProcessors() * 2 + 1;

    /**
     * 线程空闲的时候 超过时间 自动销毁
     */
    public static int surviveTime = 6000;

    /**
     * 默认的线程池
     */
    static ThreadPoolI mdefaultNormalPool;
    /**
     * 自定义的线程池
     **/
    static ThreadPoolI mSelfPool;
    /**
     * 执行周期任务的线程池
     */
    static ScheduledThreadPool mScheduledPool;
    /**
     * 单线程池
     */
    static ThreadPoolI mSinglePool;

    /**
     * 创建了一个默认普通的线程池
     */
    public static ThreadPoolI getDefaultNormalPool() {
        if (mdefaultNormalPool == null) {
            synchronized (ThreadFactory.class) {
                if (mdefaultNormalPool == null) {
                    mdefaultNormalPool = new ThreadPoolProxy(poolMinSize, poolMaxSize, surviveTime);
                }
            }
        }
        return mdefaultNormalPool;
    }

    /**
     * 自定义的普通线程池
     */
    public static ThreadPoolI getSelfPool() {
        return mSelfPool;
    }

    /**
     * 初始化自定义线程
     */
    public static boolean initSelfPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        if (mSelfPool == null) {
            synchronized (ThreadFactory.class) {
                if (mSelfPool == null) {
                    mSelfPool = new ThreadPoolProxy(corePoolSize, maximumPoolSize, keepAliveTime);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 创建一个执行周期性任务的线程池
     */
    public static ScheduledThreadPool getScheduledPool() {
        if (mScheduledPool == null) {
            synchronized (ScheduledThreadPool.class) {
                if (mScheduledPool == null) {
                    mScheduledPool = new ScheduledThreadPool(5);
                }
            }
        }
        return mScheduledPool;
    }


    /**
     * 创建一个单线程池
     *
     * @return
     */
    public static ThreadPoolI getSinglePool() {
        if (mSinglePool == null) {
            synchronized (SingleThreadPool.class) {
                if (mSinglePool == null) {
                    mSinglePool = new SingleThreadPool();
                }
            }
        }
        return mSinglePool;
    }
}
