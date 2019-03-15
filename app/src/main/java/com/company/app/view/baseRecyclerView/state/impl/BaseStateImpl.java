package com.company.app.view.baseRecyclerView.state.impl;


import com.company.app.view.baseRecyclerView.state.BaseState;

/**
 * Created by yinshuai on 2017/10/25.
 *
 * @author yinshuai
 */
public class BaseStateImpl implements BaseState {

    /**
     * state bit table
     * 12345678
     * 00000000
     *
     * 8:edit    mark   0 is normal , 1 is edit
     * 7:select  mark   0 is normal , 1 is select
     */
    private int state = STATE_FLAG_NORMAL;

    @Override
    public boolean checkState(int flagInt) {
        return (state & flagInt) != 0;
    }

    @Override
    public void addState(int flagInt) {
        changeState(flagInt, true);
    }

    @Override
    public void removeState(int flagInt) {
        changeState(flagInt, false);
    }

    @Override
    public void toggleState(int flagInt) {
        if (checkState(flagInt)) {
            removeState(flagInt);
        } else {
            addState(flagInt);
        }
    }

    @Override
    public void changeState(int flagInt, boolean set) {
        if (set) {
            state |= flagInt;
        } else {
            state &= ~flagInt;
        }

        if (onStateChangeListener != null) {
            onStateChangeListener.onStateChanged(this, flagInt, set);
        }
    }

    private OnStateChangeListener onStateChangeListener;

    @Override
    public void setOnStateChangeListener(OnStateChangeListener listener) {
        onStateChangeListener = listener;
    }
}