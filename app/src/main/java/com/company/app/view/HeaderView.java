package com.company.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.app.R;
import com.company.app.global.G;
import com.company.app.utils.ToastUtils;
import com.company.app.view.alert.BaseAlert;

/**
 * @author yinshuai
 * @date 2018/4/22
 */

public class HeaderView extends RelativeLayout implements View.OnClickListener, View.OnLongClickListener {
    private Context mContext;
    private TextView title;
    private ImageView back;
    private View line;
    private OnBackListener onBackListener;


    public HeaderView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HeaderView);
        setTitle(array.getString(R.styleable.HeaderView_header_title));
        isShowLine(array.getBoolean(R.styleable.HeaderView_header_showLine, true));
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_activity_header, this);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        line = findViewById(R.id.line);
        back.setOnClickListener(this);
        title.setOnLongClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title:

                break;
            case R.id.back:
                if (onBackListener != null) {
                    onBackListener.backClick();
                } else {
                    if (mContext != null && mContext instanceof Activity) {
                        ((Activity) mContext).finish();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            this.title.setText(title);
        }
    }

    public void isShowLine(boolean show) {
        this.line.setVisibility(show ? VISIBLE : GONE);
    }

    @Override
    public boolean onLongClick(View v) {
        if (G.isTest()) {
            ToastUtils.showToast(mContext, mContext.getClass().getName());
            new BaseAlert(mContext, "Class", mContext.getClass().getName(), "知道了", null).show();
        }
        return true;
    }

    public interface OnBackListener {
        void backClick();
    }

    public void setOnBackListener(OnBackListener onBackListener) {
        this.onBackListener = onBackListener;
    }
}
