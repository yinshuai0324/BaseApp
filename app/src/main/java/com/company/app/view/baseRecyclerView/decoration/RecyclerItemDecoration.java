package com.company.app.view.baseRecyclerView.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.company.app.R;
import com.company.app.application.MainApplication;
import com.company.app.utils.DisplayUtil;


/**
 * Created by yinshuai on 2018/1/11.
 *
 * @author yinshuai
 *         <p>
 *         可以控制分割线长度和颜色的ItemDecoration
 */

public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {

    private int decorationHeight;
    private int decorationColor = -1;
    private Paint decorationPaint;

    private int paddingLeft = 0;
    private int paddingRight = 0;
    private boolean isRefreshView = false;//是否有刷新控件
    private boolean isDrawTopLine = false;


    protected static final int SECTION_HEADER_VIEW = 1092;


    //顶部和底部是否也要绘制分割线
    private boolean isTopBottomdrawLine = false;

    public RecyclerItemDecoration() {
        initConfig();
    }

    public RecyclerItemDecoration(float diviHeight, boolean isTopBottomdrawLine, boolean isRefreshView) {
        decorationHeight = DisplayUtil.dip2px(MainApplication.getmContext(), diviHeight);
        this.isTopBottomdrawLine = isTopBottomdrawLine;
        this.isRefreshView = isRefreshView;
        initConfig();
    }

    public RecyclerItemDecoration(float diviHeight, int diviHeightColor, boolean isTopBottomdrawLine, boolean isRefreshView) {
        decorationHeight = DisplayUtil.dip2px(MainApplication.getmContext(), diviHeight);
        decorationColor = ContextCompat.getColor(MainApplication.getmContext(), diviHeightColor);
        this.isTopBottomdrawLine = isTopBottomdrawLine;
        this.isRefreshView = isRefreshView;
        initConfig();
    }

    public RecyclerItemDecoration(float diviHeight, int diviHeightColor, float paddingLeft, boolean isTopBottomdrawLine, boolean isRefreshView) {
        decorationHeight = DisplayUtil.dip2px(MainApplication.getmContext(), diviHeight);
        decorationColor = ContextCompat.getColor(MainApplication.getmContext(), diviHeightColor);
        this.isTopBottomdrawLine = isTopBottomdrawLine;
        this.paddingLeft = DisplayUtil.dip2px(MainApplication.getmContext(), paddingLeft);
        this.isRefreshView = isRefreshView;
        initConfig();
    }

    public RecyclerItemDecoration(float diviHeight, int diviColor, float paddingLeft, float paddingRight, boolean isTopBottomdrawLine, boolean isRefreshView) {
        decorationHeight = DisplayUtil.dip2px(MainApplication.getmContext(), diviHeight);
        decorationColor = ContextCompat.getColor(MainApplication.getmContext(), diviColor);
        this.paddingLeft = DisplayUtil.dip2px(MainApplication.getmContext(), paddingLeft);
        this.paddingRight = DisplayUtil.dip2px(MainApplication.getmContext(), paddingRight);
        this.isTopBottomdrawLine = isTopBottomdrawLine;
        this.isRefreshView = isRefreshView;
        initConfig();
    }


    public void initConfig() {
        if (decorationColor == -1) {
            decorationColor = ContextCompat.getColor(MainApplication.getmContext(), R.color.color_F2F2F2);
        }

        if (decorationHeight == -1) {
            decorationHeight = DisplayUtil.dip2px(MainApplication.getmContext(), 1);
        }

        decorationPaint = new Paint();
        decorationPaint.setColor(decorationColor);
    }

    /**
     * 确定分割线的高度
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = decorationHeight;


        if (isTopBottomdrawLine && parent.getChildCount() == 0) {
            //顶部也需要绘制线
            outRect.top = decorationHeight;
            isDrawTopLine = true;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft() + paddingLeft;
        int right = parent.getWidth() - parent.getPaddingRight() - paddingRight;


        if (isTopBottomdrawLine) {
            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + decorationHeight;
                if (i == 0) {
                    c.drawRect(left, view.getTop() - decorationHeight, right, view.getTop(), decorationPaint);
                }
                if (parent.getAdapter().getItemViewType(i) != SECTION_HEADER_VIEW) {
                    if (isRefreshView) {
                        if (i <= childCount - 3) {
                            c.drawRect(left, top, right, bottom, decorationPaint);
                        }
                    } else {
                        c.drawRect(left, top, right, bottom, decorationPaint);
                    }
//                    if ((i == childCount - 1||i==childCount-2) && isRefreshView) {
//
//                    } else {
//                        c.drawRect(left, top, right, bottom, decorationPaint);
//                    }

                }
            }
        } else {

            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + decorationHeight;
                c.drawRect(left, top, right, bottom, decorationPaint);
//                if (parent.getAdapter().getItemViewType(i) != SECTION_HEADER_VIEW) {
//                    c.drawRect(left, top, right, bottom, decorationPaint);
//                }
            }
        }


    }
}
