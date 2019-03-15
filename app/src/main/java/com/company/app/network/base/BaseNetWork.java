package com.company.app.network.base;


import com.company.app.global.Key;
import com.company.app.network.url.AppUrl;

/**
 * @author yinshuai
 * @date 2018/4/23
 */

public class BaseNetWork<T extends BaseWay> implements AppUrl, Key {
    private T View;

    public T getView() {
        return View;
    }

    public void setView(T view) {
        View = view;
    }

    public void showLoading() {
        getView().showLoading();
    }

    public void showLoading(String s) {
        getView().showLoading(s);
    }

    public void hideLoading() {
        getView().hideLoading();
    }

    public void showToast(String msg) {
        getView().showToast(msg);
    }

    public void showErrorToast(String msg) {
        getView().showErrorToast(msg);
    }

    public void showInfoToast(String msg) {
        getView().showInfoToast(msg);
    }

    public void showSuccessToast(String msg) {
        getView().showSuccessToast(msg);
    }

    public void showWarningToast(String msg) {
        getView().showWarningToast(msg);
    }
}
