package com.company.app.interfaces.fragment;

import android.view.View;

import com.company.app.R;
import com.company.app.base.BaseDataBindingFragment;
import com.company.app.base.BaseFragment;
import com.company.app.databinding.FragmentHome3Binding;
import com.company.app.mp.presenter.HomeFragment3Presenter;

/**
 * 描述:
 * 创建时间：2019/3/8-11:06 AM
 *
 * @author: yinshuai
 */
public class HomeFragment3 extends BaseDataBindingFragment<FragmentHome3Binding, HomeFragment3Presenter> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_3;
    }

    @Override
    protected void initView(FragmentHome3Binding binding) {

    }
}
