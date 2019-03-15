package com.company.app.network.config;

/**
 * Created by yinshuai on 2018/4/19.
 */

public interface JsonKey {
    String KEY_SUCCEED="succeed";
    String KEY_MESSAGE="msg";
    String KEY_DATA="obj";
    String KEY_CODE="code";

    /**
     * token失效或已过期
     */
    int CODE_TOKEN_LOSE = 700;
}
