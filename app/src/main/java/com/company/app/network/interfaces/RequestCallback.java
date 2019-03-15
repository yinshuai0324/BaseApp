package com.company.app.network.interfaces;

import android.content.Context;

/**
 * @author yinshuai
 * @date 2018/4/18
 */

public interface RequestCallback<T> {

    /**
     * 解析数据
     * 加这个url参数是为了打印服务器返回的时候 能够看到是哪个url返回的数据
     */
    void convertResponse(String json);

    /**
     * 对返回数据进行操作的回调，
     * 加这个url参数是为了打印服务器返回的时候 能够看到是哪个url返回的数据
     */
    void requestSuccess(String data);

    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
     */
    void requestError(Exception error);
}
