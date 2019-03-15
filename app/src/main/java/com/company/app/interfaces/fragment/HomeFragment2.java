package com.company.app.interfaces.fragment;

import android.view.View;

import com.company.app.R;
import com.company.app.base.BaseDataBindingFragment;
import com.company.app.base.BaseFragment;
import com.company.app.databinding.FragmentHome2Binding;
import com.company.app.mp.presenter.HomeFragment2Presenter;

/**
 * 描述:
 * 创建时间：2019/3/8-11:06 AM
 *
 * @author: yinshuai
 */
public class HomeFragment2 extends BaseDataBindingFragment<FragmentHome2Binding,HomeFragment2Presenter> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_2;
    }

    @Override
    protected void initView(FragmentHome2Binding binding) {

    }


}
