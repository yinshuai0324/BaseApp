package com.company.app.network.okhttp;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * 描述:
 * 创建时间：2019/3/8-2:38 PM
 *
 * @author: yinshuai
 */
public class OkHttpManage {
    private static OkHttpManage client;

    private OkHttpClient okHttpClient;

    public MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpManage() {
        okHttpClient = new OkHttpClient();
        //可以根据需求添加不同的过滤器
    }

    public static OkHttpManage getInstance() {
        if (client == null) {
            synchronized (OkHttpManage.class) {
                if (client == null) {
                    client = new OkHttpManage();
                }
            }
        }
        return client;
    }


    /**
     * 获取okhttp实例
     *
     * @return
     */
    public OkHttpClient getClient() {
        return okHttpClient;
    }
}
