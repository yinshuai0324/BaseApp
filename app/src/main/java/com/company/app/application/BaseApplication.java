package com.company.app.application;

import android.app.Application;
import android.content.Context;


/**
 * @author yinshuai
 * @date 2018/4/17
 */

public abstract class BaseApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initSDK();
        initActivity();
        initConfig();
        initVolley();
    }

    /**
     * 初始化Sdk
     */
    protected abstract void initSDK();

    /**
     * 初始化Activity
     */
    protected abstract void initActivity();

    /**
     * 初始化配置
     */
    protected abstract void initConfig();

    /**
     * 初始化Volley
     */
    protected abstract void initVolley();

    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getmContext() {
        return mContext;
    }


}
