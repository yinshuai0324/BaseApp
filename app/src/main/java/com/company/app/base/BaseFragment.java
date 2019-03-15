package com.company.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.company.app.network.base.BaseNetWork;
import com.company.app.network.base.BaseWay;
import com.company.app.utils.ClassUtils;
import com.company.app.utils.RxToast;
import com.company.app.utils.ToastUtils;
import com.company.app.utils.jumputils.JumpAction;
import com.company.app.view.LoadingView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * @author yinshuai
 * @date 2018/4/16
 */

public abstract class BaseFragment<M extends BaseNetWork> extends Fragment implements BaseWay, JumpAction {

    private View rootView;
    private LoadingView loadingView;
    private M network;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(getLayoutId(), container, false);
            //设置对应的上下文
            if (getNet() != null) {
                getNet().setView(this);
            }
            initView(rootView);
        }
        return rootView;
    }


    /**
     * 获取布局文件id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initView(View rootView);


    /**
     * 获取网络操作层
     *
     * @return
     */
    public M getNet() {
        if (network == null) {
            try {
                network = newNetWork();
            } catch (ClassCastException e) {
            }
        }
        return network;
    }

    /**
     * 获取网络操作层的实体
     *
     * @param <M>
     * @return
     */
    private <M> M newNetWork() {
        M model = null;
        try {
            Class<Object> types = ClassUtils.getSuperClassGenricType(getClass(), 1);
            if (types != null) {
                model = (M) types.newInstance();
            }
        } catch (java.lang.InstantiationException e) {
            Logger.e(e.getMessage());
        } catch (IllegalAccessException e) {
            Logger.e(e.getMessage());
        }
        return model;
    }


    @Override
    public void onStart() {
        super.onStart();
        //注册事件总线
        if (enableEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //解除事件绑定
        EventBus.getDefault().unregister(this);
    }

    /**
     * 显示Fragment
     */
    public BaseFragment showThis() {
        showFragment();
        return this;
    }

    /**
     * 显示Fragment
     */
    public void showFragment() {

    }


    /**
     * 从其他页面返回显示时调用
     */
    public void backShowFragment() {

    }

    /**
     * 隐藏Fragment
     */
    public BaseFragment hideThis() {
        hideFragment();
        return this;
    }

    /**
     * 隐藏Fragment
     */
    public void hideFragment() {

    }

    /**
     * 显示加载框
     */
    @Override
    public void showLoading() {
        showLoading("");
    }


    /**
     * 显示加载框
     *
     * @param msg
     */
    @Override
    public void showLoading(String msg) {
        if (loadingView == null) {
            loadingView = new LoadingView(getContext());
        }
        if (TextUtils.isEmpty(msg)) {
            loadingView.setMsg("正在加载，请稍后");
        } else {
            loadingView.setMsg(msg);
        }
        if (!loadingView.isShow()) {
            loadingView.show();
        }
    }

    /**
     * 关闭加载框
     */
    @Override
    public void hideLoading() {
        if (loadingView != null && loadingView.isShow()) {
            loadingView.dismiss();
        }
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(getContext(), msg);
    }

    /**
     * 显示错误的toast
     *
     * @param message
     */
    @Override
    public void showErrorToast(String message) {
        RxToast.error(getContext(), message, Toast.LENGTH_SHORT, true).show();
    }


    /**
     * 显示信息的toast
     *
     * @param message
     */
    @Override
    public void showInfoToast(String message) {
        RxToast.info(getContext(), message, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 显示成功的toast
     *
     * @param message
     */
    @Override
    public void showSuccessToast(String message) {
        RxToast.success(getContext(), message, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 显示警告的toast
     *
     * @param message
     */
    @Override
    public void showWarningToast(String message) {
        RxToast.warning(getContext(), message, Toast.LENGTH_SHORT, true).show();
    }


    /**
     * 开启事件总线
     *
     * @return
     */
    public boolean enableEventBus() {
        return false;
    }

    /**
     * 是否预留状态栏高度
     *
     * @return
     */
    public boolean isReservedStatusBarHeight() {
        return true;
    }

    /**
     * 设置状态栏文字颜色
     * true 黑色字体
     * false 白色字体
     *
     * @return
     */
    public boolean isTransparentTextModel() {
        return false;
    }

}
