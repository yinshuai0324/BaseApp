package com.company.app.network.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by yinshuai on 2018/1/4.
 */

public class Res {

    public static Context mContext;

    public static void iniRes(Context context) {
        mContext = context;
    }

    public static boolean getBoolean(int id) {
        return mContext.getResources().getBoolean(id);
    }

    public static String getString(int id) {
        return mContext.getResources().getString(id);
    }

    public static int getInteger(int id) {
        return mContext.getResources().getInteger(id);
    }


    public static String metaGetString(String metaKey) {
        try {
            ApplicationInfo appInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(metaKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
