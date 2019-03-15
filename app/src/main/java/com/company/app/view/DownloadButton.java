package com.company.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.company.app.R;
import com.company.app.utils.DisplayUtil;


/**
 * 描述: 下载按钮
 * 创建时间：2019/3/10-1:19 AM
 *
 * @author: yinshuai
 */
public class DownloadButton extends View {

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 背景画笔
     */
    private Paint bgPoint;

    /**
     * 进度条画笔
     */
    private Paint progressPoint;

    /**
     * 文字画笔
     */
    private Paint textPoint;

    /**
     * 背景边框宽度
     */
    private float lineWidth = 0;

    /**
     * 最大进度
     */
    private long maxProgress = 100;

    /**
     * 当前百分比
     */
    private long progress = 0;

    /**
     * 当前显示的文字
     */
    private String text = "0/0";

    /**
     * 文字的基准线距离
     */
    private float distance = 0;

    /**
     * 背景样式 0填满 1线性
     */
    private int bgStyle = 0;

    /**
     * 圆角角度
     */
    private float corner = 0;

    /**
     * 文字颜色
     */
    private int textColor = 0;

    /**
     * 文字大小
     */
    private float textSize = 0;

    /**
     * 进度条颜色
     */
    private int progressColor = 0;

    /**
     * 背景颜色
     */
    private int bgColor = 0;

    /**
     * 是否显示文字
     */
    private boolean isShowText = true;

    /**
     * 背景矩形
     */
    private RectF rectF = null;

    public DownloadButton(Context context) {
        super(context);
        mContext = context;
        initPoint();
    }

    public DownloadButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DownloadButton);
        lineWidth = array.getDimension(R.styleable.DownloadButton_bgLineWidth, DisplayUtil.dip2px(mContext, 1));
        progress = array.getInt(R.styleable.DownloadButton_progress, 0);
        bgStyle = array.getInteger(R.styleable.DownloadButton_bgStyle, 0);
        corner = array.getDimension(R.styleable.DownloadButton_radiusBg, DisplayUtil.dip2px(mContext, 5));
        textColor = array.getColor(R.styleable.DownloadButton_textColors, Color.WHITE);
        textSize = array.getDimension(R.styleable.DownloadButton_textSizes, DisplayUtil.dip2px(mContext, 15));
        progressColor = array.getColor(R.styleable.DownloadButton_progressColor, Color.parseColor("#4e8bf5"));
        bgColor = array.getColor(R.styleable.DownloadButton_bgColor, Color.parseColor("#D2D5D6"));
        text = array.getString(R.styleable.DownloadButton_defaultText);
        isShowText = array.getBoolean(R.styleable.DownloadButton_isShowText, true);
        maxProgress = array.getInteger(R.styleable.DownloadButton_maxProgress, 100);
        setMaxProgress(maxProgress);
        if (TextUtils.isEmpty(text)) {
            text = "下载按钮";
        }
        initPoint();
    }

    /**
     * 初始化画笔
     */
    public void initPoint() {
        bgPoint = new Paint();
        bgPoint.setAntiAlias(true);
        bgPoint.setColor(bgColor);
        bgPoint.setStyle(Paint.Style.STROKE);
        bgPoint.setStrokeWidth(lineWidth);

        progressPoint = new Paint();
        progressPoint.setAntiAlias(true);
        progressPoint.setColor(progressColor);
        progressPoint.setStyle(Paint.Style.FILL);
        progressPoint.setStrokeWidth(lineWidth);


        if (isShowText) {
            textPoint = new Paint();
            textPoint.setAntiAlias(true);
            textPoint.setColor(textColor);
            textPoint.setTextAlign(Paint.Align.CENTER);
            textPoint.setTextSize(textSize);
            //计算baseline
            Paint.FontMetrics fontMetrics = textPoint.getFontMetrics();
            distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBg(canvas);
        drawProgress(canvas);
        if (isShowText) {
            drawText(canvas);
        }
    }


    /**
     * 绘制背景
     *
     * @param canvas
     */
    public void drawBg(Canvas canvas) {
        rectF = new RectF(getPaddingLeft() + lineWidth, getPaddingTop() + lineWidth, getWidth() - getPaddingRight() - lineWidth, getHeight() - getPaddingBottom() - lineWidth);
        canvas.drawRoundRect(rectF, corner, corner, bgPoint);
    }


    /**
     * 绘制进度条
     *
     * @param canvas
     */
    public void drawProgress(Canvas canvas) {
        float lineW = lineWidth / 2;
        RectF rectF = null;
        if (bgStyle == 0) {
            float right = (getWidth() - getPaddingRight() - lineW) * progress / maxProgress;
            rectF = new RectF(getPaddingLeft() + lineW, getPaddingTop() + lineW, right, getHeight() - getPaddingBottom() - lineW);
        } else if (bgStyle == 1) {
            float right = (getWidth() - getPaddingRight() - lineWidth - lineW) * progress / maxProgress;
            rectF = new RectF(getPaddingLeft() + lineWidth + lineW, getPaddingTop() + lineWidth + lineW, right, getHeight() - getPaddingBottom() - lineWidth - lineW);
        }
        canvas.drawRoundRect(rectF, corner, corner, progressPoint);
    }


    /**
     * 设置进度条
     *
     * @param progress
     */
    public void setProgress(long progress) {
        this.progress = progress;
        invalidate();
    }


    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        invalidate();
    }


    /**
     * 设置最大进度
     *
     * @param maxProgress
     */
    public void setMaxProgress(long maxProgress) {
        this.maxProgress = maxProgress;
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    public void drawText(Canvas canvas) {
        float baseline = rectF.centerY() + distance;
        canvas.drawText(text, rectF.centerX(), baseline, textPoint);
    }

}
