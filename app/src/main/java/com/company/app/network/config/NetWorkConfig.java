package com.company.app.network.config;

/**
 * @author yinshuai
 * @date 2018/4/19
 * 网络配置项
 */

public interface NetWorkConfig {

    /**
     * 测试基地址
     */
    String BASE_TEST_URL = "http://47.104.186.84:6500/";

    /**
     * 正式基地址
     */
    String BASE_PRO_URL = "http://47.104.186.84:6500/";

    /**
     * 网络头
     */
    String HTTP_DEAL = "http://";

    /**
     * 超时时间
     */
    int TIME_OUT = 10000;

    /**
     * 失败重试次数
     */
    int RESET_COUNT = 2;


}
