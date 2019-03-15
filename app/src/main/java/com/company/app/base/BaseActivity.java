package com.company.app.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.company.app.R;
import com.company.app.network.GetData;
import com.company.app.network.base.BaseNetWork;
import com.company.app.network.base.BaseWay;
import com.company.app.utils.AppManager;
import com.company.app.utils.ClassUtils;
import com.company.app.utils.RxToast;
import com.company.app.utils.ToastUtils;
import com.company.app.utils.jumputils.JumpAction;
import com.company.app.view.LoadingView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * 描述: activity基类
 * 创建时间：2018/11/1-10:37 AM
 *
 * @author yinshuai
 */
public abstract class BaseActivity<T extends ViewDataBinding, M extends BaseNetWork> extends AppCompatActivity implements BaseWay, JumpAction {
    private M network;
    private T binding;
    private LoadingView loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getResLayout());
        //初始化View
        initView(binding);
        initialize();
        AppManager.getAppManager().addActivity(this);
        //设置对应的上下文
        if (getNet() != null) {
            getNet().setView(this);
        }

        //注册事件总线
        if (enableEventBus()) {
            EventBus.getDefault().register(this);
        }

    }

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
        } catch (InstantiationException e) {
            Logger.e(e.getMessage());
        } catch (IllegalAccessException e) {
            Logger.e(e.getMessage());
        }
        return model;
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


    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 获取当前的上下文
     *
     * @return
     */
    public Activity getContext() {
        return this;
    }


    /**
     * 获取跳转的参数
     *
     * @return
     */
    public HashMap<String, Object> getJumpData() {
        return (HashMap<String, Object>) this.getIntent().getSerializableExtra("_map");
    }

    /**
     * 开启事件总线
     *
     * @return
     */
    public boolean enableEventBus() {
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (enableEventBus()) {
            //解除事件绑定
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 获取布局资源id
     *
     * @return
     */
    protected abstract int getResLayout();

    /**
     * 初始化视图
     */
    protected abstract void initView(T binding);

    /**
     * 初始化操作
     */
    protected abstract void initialize();

}
