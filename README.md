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

#### 网络请求
网络请求是基于okhttp做了一层封装，根据泛型对象自动解析成相应的实体类，使用了RxJava进行了线程的切换，所以网络请求的回调是在UI线程的，可以直接更新UI操作，在P层可以直接调用底层的showLoading等公共方法来实现显示加载框，显示Toast等操作 

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

#### MVP架构使用说明
该项目使用的是google的DataBinding来实现控件绑定的，所以一般来说写页面只需要继承至DataBindingBaseActivity或者DataBindingBaseFragment即可，务必在布局文件的最外层加上layout标签，这样才会自动生成相应的数据绑定

- 使用步骤：
新建Activity 在布局文件的最外面加上layout标签，然后Rebuild一下项目，这个时候会自动生成一个和你布局文件名字开头的一个DataBinding文件，比如你的布局文件名字是main_activity.xml,那么就会自动生成MainActivityBind这个类，这个类里面包含你布局文件里面的所有控件
然后新建P层，一般来说是在mp文件夹下的presenter文件夹下，这个P层主要用来将网络请求与业务解偶，比如我新建MianActivityPresenter，MianActivityPresenter这个文件有一个泛型参数，用来绑定到目标的Activity，比如ActivityMianPresenter<MainActivity>,这时P层新建完毕
将自己的Activity继承至DataBindingBaseActivity或者DataBindingBaseFragment填入上面所说的两个泛型参数，比如MainActivity  extends BaseActivity<MainActivityBind, MianActivityPresenter> 复写相应的方法即可，具体代码可以参考HomeActivity
