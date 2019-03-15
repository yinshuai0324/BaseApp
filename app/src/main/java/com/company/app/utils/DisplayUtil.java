package com.company.app.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.company.app.application.MainApplication;


/**
 * dp、sp 转换为 px 的工具类
 * Created by ty
 * on 2016/4/26.
 */
public class DisplayUtil {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            context = MainApplication.getmContext();
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕信息，
     *
     * @return 数组0为宽，数组1为高
     */
    public static int[] GetDisplayInfo(Activity activity) {
        int[] info = new int[2];
        if (activity != null) {
            //创建屏幕显示度量对象
            DisplayMetrics dm = new DisplayMetrics();
            //获取屏幕管理器--->获取默认的显示对象--->获取度量对象
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            //宽
            int w = dm.widthPixels;
            //高
            int h = dm.heightPixels;
            info[0] = w;
            info[1] = h;
        }
        return info;
    }
}
