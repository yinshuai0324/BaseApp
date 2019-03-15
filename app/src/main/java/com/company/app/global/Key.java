package com.company.app.global;

/**
 * Created by yinshuai on 2018/4/23.
 */

public interface Key {
    /**
     * 成功标记
     */
    int SUCCEED = 200;
    /**
     * 用户信息
     */
    String USER_INFO = "UserInfo";

    /********跳转tag********/

    /**
     * fragment刷新标记
     */
    int REFRESH_UI = 10023;

    /**
     * 跳转到首页的标记
     */
    String MAIN_TAG_KEY = "mainTagKey";

    /**
     * 跳转到首页是否要刷新
     */
    String MAIN_TAG_REFRESH = "mainTagRefresh";
}
