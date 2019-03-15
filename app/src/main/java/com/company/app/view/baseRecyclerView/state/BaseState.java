package com.company.app.view.baseRecyclerView.state;

/**
 * Created by yinshuai on 2017/10/25.
 *
 * @author yinshuai
 */
public interface BaseState {

    /**
     * state bit table
     * 12345678
     * 00000000
     *
     * 8:edit    mark   0 is normal , 1 is edit
     * 7:select  mark   0 is normal , 1 is select
     */
    int STATE_FLAG_NORMAL    = 0x00000000;
    int STATE_FLAG_EDIT      = 0x00000001;
    int STATE_FLAG_SELECT    = 0x00000010;

    /**
     * 是否有state
     * @param state
     * @return
     */
    boolean checkState(int state);

    /**
     * 添加state
     * @param state
     */
    void addState(int state);

    /**
     * 移除state
     * @param state
     */
    void removeState(int state);

    /**
     * 切换state
     * @param state
     */
    void toggleState(int state);

    /**
     * 改变state
     * @param state
     * @param set {true:添加state,false:移除state}
     */
    void changeState(int state, boolean set);


    interface OnStateChangeListener {
        void onStateChanged(BaseState baseState, int state, boolean set);
    }

    /**
     * state 监听
     * @param listener
     */
    void setOnStateChangeListener(OnStateChangeListener listener);
}