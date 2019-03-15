package com.company.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.company.app.R;


/**
 * Created by yinshuai on 2017/9/19.
 *
 * @author yinshuai
 */
public class LoadingView {
    private Context mContext;
    private String msg;

    private ImageView iv_img;
    private TextView tv_msg;
    private LottieAnimationView loadingAnim;
    private Dialog dialog;


    private boolean isOutside = false;
    private boolean isBack = true;

    public LoadingView(Context context) {
        mContext = context;
        createLoadingView();
    }

    public LoadingView(Context context, String msg) {
        mContext = context;
        this.msg = msg;
        createLoadingView();
    }

    public LoadingView(Context context, String msg, boolean outside) {
        mContext = context;
        this.msg = msg;
        this.isOutside = outside;
        createLoadingView();
    }

    public LoadingView(Context context, String msg, boolean outside, boolean back) {
        mContext = context;
        this.msg = msg;
        this.isOutside = outside;
        this.isBack = back;
        createLoadingView();
    }

    public void createLoadingView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.loadling_view, null);
        loadingAnim = view.findViewById(R.id.animView);
        tv_msg = view.findViewById(R.id.loading_msg);

        loadingAnim.setAnimation("material_wave_loading.json");
        loadingAnim.loop(true);

        tv_msg.setText(msg);

        dialog = new Dialog(mContext, R.style.loading);
        // 是否可以按“返回键”消失
        dialog.setCancelable(isBack);
        // 点击加载框以外的区域
        dialog.setCanceledOnTouchOutside(isOutside);
        // 设置布局
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

    }

    public Dialog show() {
        loadingAnim.playAnimation();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    public LoadingView setMsg(String msg) {
        tv_msg.setText(msg);
        return this;
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public boolean isShow() {
        if (dialog != null) {
            return dialog.isShowing();
        }

        return true;
    }
}
