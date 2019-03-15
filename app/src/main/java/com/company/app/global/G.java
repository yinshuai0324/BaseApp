package com.company.app.global;

import com.company.app.R;
import com.company.app.mp.model.UserInfo;
import com.company.app.network.utils.Res;
import com.orhanobut.logger.Logger;


/**
 * Created by yinshuai on 2018/4/23.
 */

public class G implements Key {

    /**
     * 是否登陆
     *
     * @return
     */
    public static boolean isLogin() {
        return S.getObj(USER_INFO) != null ? true : false;
    }


    /**
     * 获取用户实体类
     *
     * @return
     */
    public static UserInfo getUser() {
        if (isLogin()) {
            return (UserInfo) S.getObj(USER_INFO);
        } else {
            Logger.e("用户未登陆");
            return null;
        }
    }

    /**
     * 获取用户id
     *
     * @return
     */
    public static String getUserId() {
        if (isLogin()) {
            return getUser().getUserId();
        } else {
            return "";
        }
    }


    /**
     * 获取Token
     */
    public static String getToken() {
        if (isLogin()) {
            return getUser().getUserToken();
        } else {
            return "";
        }

    }


    /**
     * 是否是测试环境
     *
     * @return
     */
    public static boolean isTest() {
        return Res.getBoolean(R.bool.isTest);
    }
}
