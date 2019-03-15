package com.company.app.network.base;

/**
 * @author yinshuai
 * @date 2018/4/23
 * 网络操作层共有的方法
 */

public interface BaseWay {

    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 显示加载框
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 隐藏加载框
     */
    void hideLoading();

    /**
     * showToast
     *
     * @param message
     */
    void showToast(String message);

    /**
     * 显示错误的toast
     *
     * @param message
     */
    void showErrorToast(String message);

    /**
     * 显示信息toast
     *
     * @param message
     */
    void showInfoToast(String message);

    /**
     * 显示成功的toast
     *
     * @param message
     */
    void showSuccessToast(String message);

    /**
     * 显示警告的toast
     *
     * @param message
     */
    void showWarningToast(String message);

}
