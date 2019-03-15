package com.company.app.network.config;

import android.content.Context;

import com.company.app.R;
import com.company.app.network.utils.Res;

/**
 * @author yinshuai
 * @date 2018/4/20
 */

public class NetConfig implements NetWorkConfig {
    public static Context mContext;
    public static String baseUrl;
    public static boolean isTest = true;

    public static void initNetWorkConfig(Context context) {
        mContext = context;
        isTest = Res.getBoolean(R.bool.isTest);
        if (isTest) {
            baseUrl = BASE_TEST_URL;
        } else {
            baseUrl = BASE_PRO_URL;
        }
    }
}
