package com.company.app.interfaces.fragment;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.company.app.R;
import com.company.app.base.BaseDataBindingFragment;
import com.company.app.base.BaseFragment;
import com.company.app.databinding.FragmentHome1Binding;
import com.company.app.mp.model.HomeFragmentTitleBean;
import com.company.app.mp.presenter.HomeFragment1Presenter;
import com.company.app.utils.jumputils.JumpActivity;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * 描述:
 * 创建时间：2019/3/8-11:06 AM
 *
 * @author: yinshuai
 */
public class HomeFragment1 extends BaseDataBindingFragment<FragmentHome1Binding, HomeFragment1Presenter> {
    private FragmentHome1Binding binding;
    private String[] ssss = new String[]{};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_1;
    }

    @Override
    protected void initView(FragmentHome1Binding binding) {
        this.binding = binding;

        binding.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNet().testRequestNet();
            }
        });

        binding.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpActivity.jump(getActivity(), ACTIVITY_DOWNLOAD);
            }
        });
    }


    public void reQuesetSucceed(List<HomeFragmentTitleBean> model) {
        Logger.i("请求成功");
        Logger.i("title大小:" + model.size());
        Logger.i(JSON.toJSONString(model));

        for (int i = 0; i < model.size(); i++) {
            HomeFragmentTitleBean bean = model.get(i);
        }
    }


}
