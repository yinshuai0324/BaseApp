package com.company.app;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.company.app.base.BaseActivity;
import com.company.app.thread.ThreadFactory;
import com.company.app.utils.jumputils.JumpActivity;

/**
 * @author yinshuai
 * @描述 启动Activity
 */
public class LaunchActivity extends BaseActivity {

    @Override
    protected int getResLayout() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initView(ViewDataBinding binding) {

    }

    @Override
    protected void initialize() {
        ThreadFactory.getScheduledPool().executeDelay(new Runnable() {
            @Override
            public void run() {
                JumpActivity.jump(LaunchActivity.this, ACTIVITY_HOME, true);
            }
        }, 1000);
    }
}
