package com.company.app.view.baseRecyclerView.inter;


import android.databinding.ViewDataBinding;

import com.company.app.view.baseRecyclerView.BaseRecyclerView;
import com.company.app.view.baseRecyclerView.state.impl.BaseStateImpl;


/**
 * Created by yinshuai on 2017/10/24.
 *
 * @author yinshuai
 */
public abstract class BaseRecyclerViewBean extends BaseStateImpl {

    public abstract int getViewType();

    public abstract void onViewDataBinding(ViewDataBinding viewDataBinding);

    protected BaseRecyclerView baseRecyclerView;

    public void bindBaseRecyclerView(BaseRecyclerView baseRecyclerView) {
        this.baseRecyclerView = baseRecyclerView;
    }


}