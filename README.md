# #一套基础的MVP APP框架
大部分的基础功能已经封装到里面了，直接可以把代码拉下来开始写页面

已经有以下功能
1. BaseActivity
2. BaseFragment
3. 带DataBinding的BaseActivity和BaseFragment
4. okhttp网络请求
5. okhttp断点续传下载
6. HeaderView
7. baseRecyclerView
8. LoadingView
9. Alert
10. 数据本地化
11. MVP架构模式
12. 线程池
13. RxToast

## 部分功能使用说明

okhttp post请求

```
RequestParams params = new RequestParams();
params.addParams("uid", 11);
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
```