package com.company.app.mp.presenter;

import com.company.app.interfaces.fragment.HomeFragment1;
import com.company.app.mp.model.HomeFragmentTitleBean;
import com.company.app.network.GetData;
import com.company.app.network.base.BaseNetWork;
import com.company.app.network.callback.ApiManageCallback;
import com.company.app.network.entity.NetWorkData;
import com.company.app.network.entity.RequestParams;
import com.company.app.network.error.Errorinfo;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * 描述:
 * 创建时间：2019/3/8-11:34 AM
 *
 * @author: yinshuai
 */
public class HomeFragment1Presenter extends BaseNetWork<HomeFragment1> {

    public void testRequestNet() {
        showLoading("正在加载");
        RequestParams params = new RequestParams();
        params.addParams("uid", 1);
        params.addParams("token", "6FA5AC088B5C97A4F66608D21B9D07AE");

        GetData.PostRequest("appApi/getCatalogList/", params, new ApiManageCallback<List<HomeFragmentTitleBean>>() {
            @Override
            protected void onSucceed(NetWorkData<List<HomeFragmentTitleBean>> result) {
                if (result.getCode() == 0 && result.getData() != null) {
                    getView().reQuesetSucceed(result.getData());
                    showSuccessToast("请求成功");
                } else {
                    showWarningToast("请求失败");
                }
                hideLoading();
            }

            @Override
            protected void onFailure(Errorinfo error) {
                showWarningToast("请求失败");
                Logger.e("请求失败:" + error.getMessage());
                hideLoading();
            }
        });
    }
}
