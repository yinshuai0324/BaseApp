package com.company.app.view.baseRecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.company.app.R;
import com.company.app.view.baseRecyclerView.inter.BaseRecyclerViewBean;
import com.company.app.view.baseRecyclerView.state.BaseState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by yinshuai on 2017/10/24.
 *
 * @author yinshuai
 */
public class BaseRecyclerView extends RecyclerView implements BaseState {

    private Context mContext;

    private float mDividerHeight = 0;
    private int mDividerColor = 0;
    private Paint mDividerPaint;
    private boolean mIsTopBottomDrawline = false;
    private float mDividerMarginLeft = 0;
    private float mDividerMarginRight = 0;

    private List<BaseRecyclerViewBean> mBaseRecyclerViewHeaders = new ArrayList<>();
    private List<BaseRecyclerViewBean> mBaseRecyclerViewBeans = new ArrayList<>();
    private List<BaseRecyclerViewBean> mBaseRecyclerViewFooters = new ArrayList<>();

    private BaseRecyclerViewAdapter mBaseRecyclerViewAdapter;

    public BaseRecyclerView(Context context) {
        this(context, null);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseRecyclerView);
        mDividerHeight = a.getDimension(R.styleable.BaseRecyclerView_dividerHeight, 0);
        mDividerColor = a.getColor(R.styleable.BaseRecyclerView_dividerColor, ContextCompat.getColor(context, R.color.color_F2F2F2));
        mDividerMarginLeft = a.getDimension(R.styleable.BaseRecyclerView_dividerMarginLeft, 0);
        mDividerMarginRight = a.getDimension(R.styleable.BaseRecyclerView_dividerMarginRight, 0);
        mIsTopBottomDrawline = a.getBoolean(R.styleable.BaseRecyclerView_isTopBottomDrawline, false);

        initView();
    }

    private boolean mNeedInterceptTouchEvent = true;
    private float xDistance, yDistance, xLast, yLast;

    /**
     * 是否需要屏蔽方向不一致的touch操作
     *
     * @param needInterceptTouchEvent
     */
    public void setNeedInterceptTouchEvent(boolean needInterceptTouchEvent) {
        this.mNeedInterceptTouchEvent = needInterceptTouchEvent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mNeedInterceptTouchEvent) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDistance = yDistance = 0f;
                    xLast = ev.getX();
                    yLast = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float curX = ev.getX();
                    final float curY = ev.getY();

                    xDistance += Math.abs(curX - xLast);
                    yDistance += Math.abs(curY - yLast);
                    xLast = curX;
                    yLast = curY;

                    if (mOrientation == OrientationHelper.VERTICAL) {
                        if (xDistance > yDistance) {
                            return false;
                        }
                    } else if (mOrientation == OrientationHelper.HORIZONTAL) {
                        if (yDistance > xDistance) {
                            return false;
                        }
                    }
                default:
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    interface OnScrollStateChangedListener {
        /**
         * 滑动状态回调
         *
         * @param view
         * @param scrollState {@link RecyclerView#SCROLL_STATE_IDLE,RecyclerView#SCROLL_STATE_DRAGGING,RecyclerView#SCROLL_STATE_SETTLING}
         */
        void onScrollStateChanged(RecyclerView view, int scrollState);
    }

    private OnScrollStateChangedListener mOnScrollStateChangedListener;

    public void setOnScrollStateChangedListener(OnScrollStateChangedListener listener) {
        mOnScrollStateChangedListener = listener;
    }


    public interface OnPercentListener {
        /**
         * 滑动时顶部渐变百分比的回调，分子为列表距离顶部的距离，分母为 {@link this#mPercentHeight}
         *
         * @param percent
         */
        void onPercent(float percent);
    }

    private int mPercentHeight = 0;
    private OnPercentListener mOnPercentListener;

    public void setOnPercentListener(OnPercentListener listener, int height) {
        mOnPercentListener = listener;
        mPercentHeight = height;
    }

    public void setOnPercentHeight(int height) {
        mPercentHeight = height;
    }

    public void setOnPercentListener(OnPercentListener listener) {
        mOnPercentListener = listener;
    }


    public interface OnFirstVisibleItemListener {
        /**
         * 滑动时回调第一个可见bean的index
         *
         * @param firstVisibleItem
         */
        void onFirstVisibleItem(int firstVisibleItem);
    }

    private OnFirstVisibleItemListener mOnFirstVisibleItemListener;

    public void setOnFirstVisibleItemListener(OnFirstVisibleItemListener listener) {
        mOnFirstVisibleItemListener = listener;
    }

    public interface OnScrollToStartListener {
        /**
         * 滑动到第一个bean时回调
         *
         * @param clamped {true:已经到了边界,false:还没有到边界}
         */
        void onStart(boolean clamped);
    }

    private OnScrollToStartListener mOnScrollToStartListener;

    public void setOnScrollToStartListener(OnScrollToStartListener listener) {
        mOnScrollToStartListener = listener;
    }


    public interface OnScrollToEndListener {
        /**
         * 滑动到最后一个bean时回调
         *
         * @param clamped {true:已经到了边界,false:还没有到边界}
         */
        void onEnd(boolean clamped);
    }

    private OnScrollToEndListener mOnScrollToEndListener;

    public void setOnScrollToEndListener(OnScrollToEndListener listener) {
        mOnScrollToEndListener = listener;
    }

    public interface OnLoadMoreListener {
        /**
         * 加载更多回调
         */
        void onLoadMore();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    /**
     * 方向 {@link OrientationHelper#VERTICAL,OrientationHelper#HORIZONTAL}
     */
    private int mOrientation;
    /**
     * 自身的layoutManager
     */
    private LayoutManager mLayoutManager;
    /**
     * layoutManager的类型 {@link LAYOUT_MANAGER_TYPE}
     */
    private LAYOUT_MANAGER_TYPE mLayoutManagerType;

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    private void initDividerPaint() {
        mDividerPaint = new Paint();
        mDividerPaint.setColor(mDividerColor);
    }

    /**
     * 根据layoutManager判断方向和类型等
     *
     * @param layout
     */
    @Override
    public void setLayoutManager(LayoutManager layout) {
        //'com.android.support:recyclerview-v7:25+' 此版本的RecyclerView不设置此属性会导致闪退
        layout.setItemPrefetchEnabled(false);

        super.setLayoutManager(layout);

        initDividerPaint();

        mLayoutManager = layout;
        if (layout instanceof GridLayoutManager) {

            mLayoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            GridLayoutManager gridLayoutManager = ((GridLayoutManager) mLayoutManager);
            final int SpanCount = gridLayoutManager.getSpanCount();

            mOrientation = gridLayoutManager.getOrientation();

            if (mDividerHeight != 0 && mDividerColor != 0) {
                addItemDecoration(new ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                        int position = parent.getChildAdapterPosition(view);
                        if (mOrientation == OrientationHelper.VERTICAL) {
                            int right;
                            int top;

                            if (position + 1 % SpanCount != 0) {
                                right = (int) mDividerHeight;
                            } else {
                                right = 0;
                            }

                            if (position >= SpanCount) {
                                top = (int) mDividerHeight;
                            } else {
                                top = 0;
                            }

                            outRect.set(0, top, right, 0);
                        } else if (mOrientation == OrientationHelper.HORIZONTAL) {
                            outRect.set(0, 0, 0, 0);
                        }
                    }

                });

            }

        } else if (layout instanceof LinearLayoutManager) {
            mLayoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) mLayoutManager);
            mOrientation = linearLayoutManager.getOrientation();

            if (mDividerHeight != 0 && mDividerColor != 0) {
                addItemDecoration(new ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                        if (((LinearLayoutManager) mLayoutManager).getOrientation() == OrientationHelper.HORIZONTAL) {
                            outRect.set(0, 0, (int) mDividerHeight, 0);
                        } else if (((LinearLayoutManager) mLayoutManager).getOrientation() == OrientationHelper.VERTICAL) {
                            outRect.set(0, 0, 0, (int) mDividerHeight);
                        }
                        if (mIsTopBottomDrawline && parent.getChildCount() == 0) {
                            if (((LinearLayoutManager) mLayoutManager).getOrientation() == OrientationHelper.HORIZONTAL) {
                                //顶部也需要绘制线
                                outRect.set(0, 0, (int) mDividerHeight, 0);
                            } else if (((LinearLayoutManager) mLayoutManager).getOrientation() == OrientationHelper.VERTICAL) {
                                outRect.set(0, 0, 0, (int) mDividerHeight);
                            }
                        }
                    }

                    @Override
                    public void onDraw(Canvas c, RecyclerView parent, State state) {
                        int childCount = parent.getChildCount();

                        if (((LinearLayoutManager) mLayoutManager).getOrientation() == OrientationHelper.HORIZONTAL) {
                            int top = parent.getPaddingTop();
                            int bottom = parent.getHeight() - parent.getPaddingBottom();

                            if (mIsTopBottomDrawline) {
                                for (int i = 0; i < childCount; i++) {
                                    View view = parent.getChildAt(i);
                                    float left = view.getRight();
                                    float right = view.getRight() + (int) mDividerHeight;
                                    if (i == 0) {
                                        c.drawRect(view.getLeft(), top, view.getLeft() + mDividerHeight, bottom, mDividerPaint);
                                    }
                                    c.drawRect(left, top, right, bottom, mDividerPaint);
                                }
                            } else {
                                for (int i = 0; i < childCount - 1; i++) {
                                    View view = parent.getChildAt(i);
                                    float left = view.getRight();
                                    float right = view.getRight() + (int) mDividerHeight;
                                    c.drawRect(left, top, right, bottom, mDividerPaint);
                                }
                            }
                        } else if (((LinearLayoutManager) mLayoutManager).getOrientation() == OrientationHelper.VERTICAL) {
                            int left = parent.getPaddingLeft() + (int) mDividerMarginLeft;
                            int right = parent.getWidth() - parent.getPaddingRight() - (int) mDividerMarginRight;

                            if (mIsTopBottomDrawline) {
                                for (int i = 0; i < childCount; i++) {
                                    View view = parent.getChildAt(i);
                                    float top = view.getBottom();
                                    float bottom = view.getBottom() + (int) mDividerHeight;
                                    if (i == 0) {
                                        c.drawRect(left, view.getTop(), right, view.getTop() + mDividerHeight, mDividerPaint);
                                    }
                                    c.drawRect(left, top, right, bottom, mDividerPaint);

                                }

                            } else {
                                for (int i = 0; i < childCount - 1; i++) {
                                    View view = parent.getChildAt(i);
                                    float top = view.getBottom();
                                    float bottom = view.getBottom() + (int) mDividerHeight;
                                    c.drawRect(left, top, right, bottom, mDividerPaint);
                                }
                            }

                        }

                    }
                });
            }
        } else if (layout instanceof StaggeredGridLayoutManager) {
            mLayoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) mLayoutManager);
            mOrientation = staggeredGridLayoutManager.getOrientation();
        } else {
            throw new RuntimeException(
                    "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }
    }

    private int[] positions;
    int firstVisibleItemPosition = -1;
    int lastVisibleItemPosition = -1;

    /**
     * 初始化配置、界面、监听等
     */
    private void initView() {

        setVerticalScrollBarEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {
                if (mOnScrollStateChangedListener != null) {
                    mOnScrollStateChangedListener.onScrollStateChanged(view, scrollState);
                }

//                switch (scrollState) {
//                    case RecyclerView.SCROLL_STATE_IDLE:
//                        ImageLoader.getInstance().resume();
//                        break;
//                    case RecyclerView.SCROLL_STATE_DRAGGING:
//                        ImageLoader.getInstance().pause();
//                        break;
//                    case RecyclerView.SCROLL_STATE_SETTLING:
//                        ImageLoader.getInstance().pause();
//                        break;
//                    default:
//                        break;
//                }
            }

            int lastVisibleItemPos = -1;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mOnScrolledListener != null) {
                    mOnScrolledListener.onScrolled(dx, dy);
                }

                switch (mLayoutManagerType) {
                    case LINEAR:
                        LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) mLayoutManager);
                        firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                        lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                        break;
                    case GRID:
                        GridLayoutManager gridLayoutManager = ((GridLayoutManager) mLayoutManager);
                        firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                        lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                        break;
                    case STAGGERED_GRID:
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) mLayoutManager;
                        firstVisibleItemPosition = findMin(staggeredGridLayoutManager.findFirstVisibleItemPositions(positions));
                        lastVisibleItemPosition = findMax(staggeredGridLayoutManager.findLastVisibleItemPositions(positions));
                        break;
                    default:
                        break;
                }

                int extent = -1;
                int offset = -1;
                int range = -1;

                if (mOrientation == OrientationHelper.VERTICAL) {
                    extent = computeVerticalScrollExtent();
                    offset = computeVerticalScrollOffset();
                    range = computeVerticalScrollRange();
                } else if (mOrientation == OrientationHelper.HORIZONTAL) {
                    extent = computeHorizontalScrollExtent();
                    offset = computeHorizontalScrollOffset();
                    range = computeHorizontalScrollRange();
                }

                if (mOnFirstVisibleItemListener != null) {
                    mOnFirstVisibleItemListener.onFirstVisibleItem(firstVisibleItemPosition);
                }

                if (mOnPercentListener != null && mPercentHeight > 0) {
                    if (firstVisibleItemPosition == 0) {
                        mOnPercentListener.onPercent(1f * offset / mPercentHeight);
                    } else {
                        mOnPercentListener.onPercent(1);
                    }
                }

                if (mOnScrollToStartListener != null && firstVisibleItemPosition == 0) {
                    if (offset <= 0) {
                        mOnScrollToStartListener.onStart(true);
                    } else {
                        mOnScrollToStartListener.onStart(false);
                    }
                }

                if (mOnScrollToEndListener != null) {
                    if (lastVisibleItemPosition == getCount() - 1) {
                        if (extent + offset >= range) {
                            mOnScrollToEndListener.onEnd(true);
                        } else {
                            mOnScrollToEndListener.onEnd(false);
                        }
                    }
                }

                if (mOnLoadMoreListener != null) {
                    if (lastVisibleItemPos != lastVisibleItemPosition) {
                        lastVisibleItemPos = lastVisibleItemPosition;
                        if (lastVisibleItemPosition >= getCount() - 3) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                    }
                }
            }
        });

        mBaseRecyclerViewAdapter = new BaseRecyclerViewAdapter();
        mBaseRecyclerViewAdapter.registerAdapterDataObserver(observer);
        setAdapter(mBaseRecyclerViewAdapter);
    }

    private int findMin(int[] ints) {
        int min = ints[0];
        for (int value : ints) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private int findMax(int[] ints) {
        int max = ints[0];
        for (int value : ints) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public interface OnScrolledListener {
        /**
         * 滑动距离监听
         *
         * @param dx
         * @param dy
         */
        void onScrolled(int dx, int dy);
    }

    private OnScrolledListener mOnScrolledListener;

    public void setOnScrolledListener(OnScrolledListener onScrolledListener) {
        mOnScrolledListener = onScrolledListener;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    public interface OnItemClickListener {
        /**
         * Item点击监听
         *
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    /**
     * 要写在添加bean之前
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemLongClickListener {
        /**
         * Item长按监听
         *
         * @param view
         * @param position
         * @return
         */
        boolean onItemLongClick(View view, int position);
    }

    private OnItemLongClickListener mOnItemLongClickListener;

    /**
     * 要写在添加bean之前
     *
     * @param listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    /**
     * clone beans
     *
     * @return
     */
    public List<BaseRecyclerViewBean> getBeansClone() {
        return (ArrayList) ((ArrayList) mBaseRecyclerViewBeans).clone();
    }


    /**
     * 获取beans的总数
     *
     * @return
     */
    public int getCount() {
        return mBaseRecyclerViewBeans.size();
    }

    /**
     * 获取beans
     *
     * @return
     */
    public List<BaseRecyclerViewBean> getBeans() {
        return mBaseRecyclerViewBeans;
    }

    /**
     * 获取目标bean的index
     *
     * @param bean
     * @return
     */
    public int getBeanIndex(BaseRecyclerViewBean bean) {
        return mBaseRecyclerViewBeans.indexOf(bean);
    }

    /**
     * 添加bean
     *
     * @param bean
     */
    public void addBean(BaseRecyclerViewBean bean) {
        mBaseRecyclerViewBeans.add(bean);
        bean.bindBaseRecyclerView(this);
        bean.setOnStateChangeListener(mBeanOnStateChangeListenerInner);
    }

    /**
     * 添加bean到指定位置
     *
     * @param bean
     */
    public void addBeanToPosition(BaseRecyclerViewBean bean, int position) {
        mBaseRecyclerViewBeans.add(position, bean);
        bean.bindBaseRecyclerView(this);
        bean.setOnStateChangeListener(mBeanOnStateChangeListenerInner);
    }

    /**
     * 添加bean到列表头
     *
     * @param bean
     */
    public void addBeanToFirst(BaseRecyclerViewBean bean) {
        addBeanToPosition(bean, 0);
    }

    /**
     * 添加beans
     *
     * @param beans
     */
    public void addBeans(List<BaseRecyclerViewBean> beans) {
        for (BaseRecyclerViewBean bean : beans) {
            addBean(bean);
        }
    }

    /**
     * 移除某bean
     *
     * @param index
     */
    public void removeBean(int index) {
        removeBean(mBaseRecyclerViewBeans.get(index));
    }

    /**
     * 移除某bean
     *
     * @param bean
     */
    public void removeBean(BaseRecyclerViewBean bean) {
        bean.bindBaseRecyclerView(null);
        bean.setOnStateChangeListener(null);
        mBaseRecyclerViewBeans.remove(bean);
    }

    /**
     * 移除某bean，并刷新
     *
     * @param bean
     */
    public void removeBean(BaseRecyclerViewBean bean, boolean refresh) {
        removeBean(bean);
        if (refresh) {
            notifyDataSetChanged();
        }
    }

    /**
     * 清空数据
     */
    public void clearBeans() {
        for (BaseRecyclerViewBean bean : mBaseRecyclerViewBeans) {
            bean.bindBaseRecyclerView(null);
            bean.setOnStateChangeListener(null);
        }
        mBaseRecyclerViewBeans.clear();
    }

    /**
     * 获取bean的位置
     *
     * @param bean
     * @return
     */
    public int getPositionByBean(BaseRecyclerViewBean bean) {
        for (int i = 0; i < mBaseRecyclerViewBeans.size(); i++) {
            if (bean == mBaseRecyclerViewBeans.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public void notifyDataSetChanged() {
        if (mBaseRecyclerViewAdapter != null) {
            mBaseRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public void notifyItemInserted(BaseRecyclerViewBean bean, int position) {
        addBeanToPosition(bean, position);
        if (mBaseRecyclerViewAdapter != null) {
            mBaseRecyclerViewAdapter.notifyItemInserted(position);
        }
    }

    public void notifyItemRemoved(int position) {
        removeBean(position);
        if (mBaseRecyclerViewAdapter != null) {
            mBaseRecyclerViewAdapter.notifyItemRemoved(position);
        }
    }

    public void notifyItemRemoved(BaseRecyclerViewBean bean) {
        int position = getPositionByBean(bean);
        if (position >= 0) {
            notifyItemRemoved(position);
        }
    }


    public void notifyItemChanged(BaseRecyclerViewBean bean) {
        int position = getPositionByBean(bean);
        if (position >= 0) {
            mBaseRecyclerViewAdapter.notifyItemChanged(position);
        }
    }

    /**
     * 获取index的bean
     *
     * @param index
     * @return
     */
    public BaseRecyclerViewBean getBean(int index) {
        return mBaseRecyclerViewBeans.get(index);
    }

    /**
     * 获取state的bean的总数
     *
     * @param state
     * @return
     */
    public int getBeansCountByState(int state) {
        int i = 0;
        for (BaseRecyclerViewBean baseRecyclerViewItem : mBaseRecyclerViewBeans) {
            if (baseRecyclerViewItem.checkState(state)) {
                i++;
            }
        }
        return i;
    }

    /**
     * 所有bean是否都是state
     *
     * @param state
     * @return
     */
    public boolean isAllStateInBeans(int state) {
        if (mBaseRecyclerViewBeans.size() == 0) {
            return false;
        }
        for (BaseRecyclerViewBean baseRecyclerViewItem : mBaseRecyclerViewBeans) {
            if (!baseRecyclerViewItem.checkState(state)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 所有bean添加state
     *
     * @param state {@link BaseState}
     */
    public void addStateInBeans(int state) {
        int len = mBaseRecyclerViewBeans.size();
        len--;
        for (int i = 0; i < len; i++) {
            mBaseRecyclerViewBeans.get(i).addState(state);
        }
        mBaseRecyclerViewBeans.get(len).addState(state);
    }

    /**
     * 所有类移除state
     *
     * @param state {@link BaseState}
     */
    public void removeStateInBeans(int state) {
        int len = mBaseRecyclerViewBeans.size();
        len--;
        for (int i = 0; i < len; i++) {
            mBaseRecyclerViewBeans.get(i).removeState(state);
        }
        mBaseRecyclerViewBeans.get(len).removeState(state);
    }

    /**
     * 获取有state标志的bean
     *
     * @param state {@link BaseState}
     * @return
     */
    public List<BaseRecyclerViewBean> getBeansByState(int state) {
        ArrayList<BaseRecyclerViewBean> items = new ArrayList<>();
        for (BaseRecyclerViewBean baseRecyclerViewItem : mBaseRecyclerViewBeans) {
            if (baseRecyclerViewItem.checkState(state)) {
                items.add(baseRecyclerViewItem);
            }
        }
        return items;
    }

    /**
     * 移除有state标志的bean
     *
     * @param state {@link BaseState}
     */
    public void removeBeansByState(int state) {
        Iterator<BaseRecyclerViewBean> iterator = mBaseRecyclerViewBeans.iterator();
        while (iterator.hasNext()) {
            BaseRecyclerViewBean baseRecyclerViewItem = iterator.next();
            if (baseRecyclerViewItem.checkState(state)) {
                iterator.remove();
            }
        }
    }

    /**
     * 移除某类型全部bean
     *
     * @param bean
     */
    public void removeBeanByType(Class bean) {
        Iterator<BaseRecyclerViewBean> iterator = mBaseRecyclerViewBeans.iterator();
        while (iterator.hasNext()) {
            BaseRecyclerViewBean baseRecyclerViewItem = iterator.next();
            if (baseRecyclerViewItem.getClass().getSimpleName().equals(bean.getSimpleName())) {
                iterator.remove();
            }
        }
    }

    /**
     * 添加header
     *
     * @param bean
     */
    public void addHeader(BaseRecyclerViewBean bean) {
        bean.bindBaseRecyclerView(this);
        mBaseRecyclerViewHeaders.add(bean);
    }

    /**
     * 清空headers
     */
    public void clearHeaders() {
        for (BaseRecyclerViewBean bean : mBaseRecyclerViewHeaders) {
            bean.bindBaseRecyclerView(null);
            bean.setOnStateChangeListener(null);
        }
        mBaseRecyclerViewHeaders.clear();
    }

    /**
     * 获取headers的数目
     *
     * @return
     */
    public int getHeadersCount() {
        return mBaseRecyclerViewHeaders.size();
    }

    /**
     * 添加footer
     *
     * @param bean
     */
    public void addFooter(BaseRecyclerViewBean bean) {
        bean.bindBaseRecyclerView(this);
        mBaseRecyclerViewFooters.add(bean);
    }

    /**
     * 清空footers
     */
    public void clearFooters() {
        for (BaseRecyclerViewBean bean : mBaseRecyclerViewFooters) {
            bean.bindBaseRecyclerView(null);
            bean.setOnStateChangeListener(null);
        }
        mBaseRecyclerViewFooters.clear();
    }

    /**
     * 获取footers的数目
     *
     * @return
     */
    public int getFootersCount() {
        return mBaseRecyclerViewFooters.size();
    }

    /**
     * 适配器
     */
    public class BaseRecyclerViewAdapter extends Adapter<BaseViewHolder> {


        public BaseRecyclerViewAdapter() {
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            try {
                ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
                BaseViewHolder viewHolder = new BaseViewHolder(viewDataBinding);

                /**
                 * header 以及 footerView根据不同的适配器做不同的处理
                 */
                if (getLayoutManager() instanceof GridLayoutManager) {
                    headerAdapterGridLayoutManager();
                } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    headerAdapterStaggeredGridLayoutManager(viewDataBinding, viewType);
                }

                return viewHolder;
            } catch (Exception e) {
                return new BaseViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false));
            }


        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, final int position) {
            if (mOnItemClickListener != null) {
                holder.getBinding().getRoot().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(view, position);
                    }
                });
            }
            if (mOnItemLongClickListener != null) {
                holder.getBinding().getRoot().setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return mOnItemLongClickListener.onItemLongClick(view, position);
                    }
                });
            }
            getBeanByPosition(position).onViewDataBinding(holder.getBinding());
        }

        @Override
        public int getItemCount() {
            return mBaseRecyclerViewHeaders.size() + mBaseRecyclerViewBeans.size() + mBaseRecyclerViewFooters.size();
        }

        @Override
        public int getItemViewType(int position) {
            return getBeanByPosition(position).getViewType();
        }

        private BaseRecyclerViewBean getBeanByPosition(int position) {
            BaseRecyclerViewBean bean;
            if (position < mBaseRecyclerViewHeaders.size()) {
                bean = mBaseRecyclerViewHeaders.get(position);
            } else if (position >= mBaseRecyclerViewHeaders.size() + mBaseRecyclerViewBeans.size()) {
                bean = mBaseRecyclerViewFooters.get(position - mBaseRecyclerViewHeaders.size() - mBaseRecyclerViewBeans.size());
            } else {
                bean = mBaseRecyclerViewBeans.get(position - mBaseRecyclerViewHeaders.size());
            }
            return bean;
        }


        /**
         * recyclerView Header适配GridLayoutManager
         */
        public void headerAdapterGridLayoutManager() {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) getLayoutManager();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getBeanByPosition(position).getViewType();

                    for (int i = 0; i < mBaseRecyclerViewHeaders.size(); i++) {
                        if (mBaseRecyclerViewHeaders.get(i).getViewType() == viewType) {
                            return gridLayoutManager.getSpanCount();
                        }
                    }

                    for (int i = 0; i < mBaseRecyclerViewFooters.size(); i++) {
                        if (mBaseRecyclerViewFooters.get(i).getViewType() == viewType) {
                            return gridLayoutManager.getSpanCount();
                        }
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }

        /**
         * recyclerView Header适配StaggeredGridLayoutManager
         *
         * @param binding
         * @param viewType
         */
        public void headerAdapterStaggeredGridLayoutManager(ViewDataBinding binding, int viewType) {
            for (int i = 0; i < mBaseRecyclerViewHeaders.size(); i++) {
                if (mBaseRecyclerViewHeaders.get(i).getViewType() == viewType) {
                    staggeredGridLayoutManagerFull(binding.getRoot());
                }
            }

            for (int i = 0; i < mBaseRecyclerViewFooters.size(); i++) {
                if (mBaseRecyclerViewFooters.get(i).getViewType() == viewType) {
                    staggeredGridLayoutManagerFull(binding.getRoot());
                }
            }

        }

    }

    /**
     * 让StaggeredGridLayoutManager占满一行
     *
     * @param view
     */
    public void staggeredGridLayoutManagerFull(View view) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setFullSpan(true);
        view.setLayoutParams(layoutParams);
    }


    /**
     * 数据更改监听
     */
    final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkEmptyView();
        }
    };

    /**
     * holder
     */
    public class BaseViewHolder extends ViewHolder {

        private ViewDataBinding binding;

        public BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            setBinding(binding);
        }

        public BaseViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }

    OnStateChangeListener mBeanOnStateChangeListenerInner = new OnStateChangeListener() {
        @Override
        public void onStateChanged(BaseState baseState, int state, boolean set) {
            if (mBeanOnStateChangeListenerOuter != null) {
                mBeanOnStateChangeListenerOuter.onStateChanged(baseState, state, set);
            }
        }
    };

    OnStateChangeListener mBeanOnStateChangeListenerOuter;

    /**
     * bean state 监听
     *
     * @param listener
     */
    public void setOnBeansStateChangeListener(OnStateChangeListener listener) {
        mBeanOnStateChangeListenerOuter = listener;
    }

    private int mState = STATE_FLAG_NORMAL;

    @Override
    public boolean checkState(int state) {
        return (mState & state) != 0;
    }

    @Override
    public void addState(int state) {
        changeState(state, true);
    }

    @Override
    public void removeState(int state) {
        changeState(state, false);
    }

    @Override
    public void toggleState(int state) {
        if (checkState(state)) {
            removeState(state);
        } else {
            addState(state);
        }
    }

    @Override
    public void changeState(int state, boolean set) {
        if (set) {
            mState |= state;
        } else {
            mState &= ~state;
        }

        notifyDataSetChanged();

        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onStateChanged(this, state, set);
        }
    }

    private OnStateChangeListener mOnStateChangeListener;

    @Override
    public void setOnStateChangeListener(OnStateChangeListener listener) {
        mOnStateChangeListener = listener;
    }

    private View mEmptyView;

    /**
     * 设置空view
     *
     * @param view
     */
    public void setEmptyView(View view) {
        mEmptyView = view;
        checkEmptyView();
    }

    /**
     * 检测列表是否为空，为空的话显示空view，否则反之。
     */
    private void checkEmptyView() {
        if (mEmptyView != null) {
            if (mBaseRecyclerViewAdapter.getItemCount() > 0) {
                setVisibility(VISIBLE);
                mEmptyView.setVisibility(GONE);
            } else {
                setVisibility(GONE);
                mEmptyView.setVisibility(VISIBLE);
            }
        }
    }
}