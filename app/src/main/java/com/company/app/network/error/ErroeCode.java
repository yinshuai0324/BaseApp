package com.company.app.network.error;

/**
 * Created by yinshuai on 2018/4/19.
 */

public interface ErroeCode {
    /**
     * 服务器返回数据为空
     */
    int SERVICE_DATA_EMPTY = 10001;

    /**
     * 服务器返回data字段为空
     */
    int SERVICE_DATA_DATA_EMPTY = 10002;


    /**
     * 解析数据失败
     */
    int ANALYSIS_DATA_FAILUERE = 10003;

    /**
     * 解析data字段失败
     */
    int ANALYSIS_DATA_DATA_FAILUERE = 10004;

    /**
     * okhttp框架报错
     */
    int OKHTTP_ERROR = 10005;


}
