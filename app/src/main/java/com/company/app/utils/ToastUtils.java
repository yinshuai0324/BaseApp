package com.company.app.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2017/3/24.
 */

public class ToastUtils {

    private Toast mToast;
    private Context context;

    public ToastUtils(Context context) {
        this.context = context.getApplicationContext();
    }

    public static void showToast(Context context, String msg){
        new ToastUtils(context).showToast(msg);
    }

    public static void showToast(Context context, int resId){
        new ToastUtils(context).showToast(resId);
    }

    public Toast getSingletonToast(int resId) {
        if(this.mToast == null) {
            this.mToast = Toast.makeText(this.context, resId, Toast.LENGTH_SHORT);
        } else {
            this.mToast.setText(resId);
        }
        return this.mToast;
    }

    public Toast getSingletonToast(String text) {
        if(this.mToast == null) {
            this.mToast = Toast.makeText(this.context, text, Toast.LENGTH_SHORT);
        } else {
            this.mToast.setText(text);
        }

        return this.mToast;
    }

    public Toast getSingleLongToast(int resId) {
        if(this.mToast == null) {
            this.mToast = Toast.makeText(this.context, resId, Toast.LENGTH_LONG);
        } else {
            this.mToast.setText(resId);
        }

        return this.mToast;
    }

    public Toast getSingleLongToast(String text) {
        if(this.mToast == null) {
            this.mToast = Toast.makeText(this.context, text, Toast.LENGTH_LONG);
        } else {
            this.mToast.setText(text);
        }

        return this.mToast;
    }

    public Toast getToast(int resId) {
        return Toast.makeText(this.context, resId, Toast.LENGTH_SHORT);
    }

    public Toast getToast(String text) {
        return Toast.makeText(this.context, text, Toast.LENGTH_SHORT);
    }

    public Toast getLongToast(int resId) {
        return Toast.makeText(this.context, resId, Toast.LENGTH_LONG);
    }

    public Toast getLongToast(String text) {
        return Toast.makeText(this.context, text, Toast.LENGTH_LONG);
    }

    public void showSingletonToast(int resId) {
        this.getSingletonToast(resId).show();
    }

    public void showSingletonToast(String text) {
        this.getSingletonToast(text).show();
    }

    public void showSingleLongToast(int resId) {
        this.getSingleLongToast(resId).show();
    }

    public void showSingleLongToast(String text) {
        this.getSingleLongToast(text).show();
    }

    public void showToast(int resId) {
        this.getToast(resId).show();
    }

    public void showToast(String text) {
        this.getToast(text).show();
    }

    public void showLongToast(int resId) {
        this.getLongToast(resId).show();
    }

    public void showLongToast(String text) {
        this.getLongToast(text).show();
    }
}
