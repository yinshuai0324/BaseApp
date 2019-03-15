package com.company.app.interfaces.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.alibaba.fastjson.JSON;
import com.company.app.R;
import com.company.app.base.BaseActivity;
import com.company.app.base.BaseDataBindingFragment;
import com.company.app.databinding.ActivityHomeBinding;
import com.company.app.global.S;
import com.company.app.interfaces.fragment.HomeFragment1;
import com.company.app.interfaces.fragment.HomeFragment2;
import com.company.app.interfaces.fragment.HomeFragment3;
import com.company.app.mp.model.HomeFragmentTitleBean;
import com.company.app.mp.model.HomeMenuBean;
import com.company.app.mp.model.TestModel;
import com.company.app.mp.presenter.TestPresenter;
import com.company.app.utils.AppManager;
import com.company.app.view.BottomToolBar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

import static com.company.app.global.Key.MAIN_TAG_KEY;
import static com.company.app.global.Key.MAIN_TAG_REFRESH;
import static com.company.app.global.Key.REFRESH_UI;

/**
 * 描述:
 * 创建时间：2018/11/1-11:55 AM
 *
 * @author: yinshuai
 */
@RuntimePermissions
public class HomeActivity extends BaseActivity<ActivityHomeBinding, TestPresenter> implements BottomToolBar.OnMenuItemClickListener {
    ActivityHomeBinding binding;

    private FragmentManager fragmentManager;

    public static final String TAG_FRAGMENT_1 = "fragment1";
    public static final String TAG_FRAGMENT_2 = "fragment2";
    public static final String TAG_FRAGMENT_3 = "fragment3";

    private HomeFragment1 fragment1;
    private HomeFragment2 fragment2;
    private HomeFragment3 fragment3;

    @Override
    protected int getResLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView(ActivityHomeBinding binding) {
        if (binding instanceof ActivityHomeBinding) {
            this.binding = binding;

            //  getNet().testRequestNet();
        }
    }


    @Override
    protected void initialize() {

        //初始化菜单选择
        List<HomeMenuBean> menus = new ArrayList<>();
        menus.add(new HomeMenuBean("菜单1", R.mipmap.test, R.mipmap.test_select, TAG_FRAGMENT_1));
        menus.add(new HomeMenuBean("菜单2", R.mipmap.test, R.mipmap.test_select, TAG_FRAGMENT_2));
        menus.add(new HomeMenuBean("菜单3", R.mipmap.test, R.mipmap.test_select, TAG_FRAGMENT_3));
        binding.buttonToolBar.setDatas(menus);
        binding.buttonToolBar.setOnMenuItemClickListener(this);
        fragmentManager = getSupportFragmentManager();
        initFragment();

        //检测权限
        HomeActivityPermissionsDispatcher.applyPermissionWithPermissionCheck(this);
    }


    public void initFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment1 == null) {
            fragment1 = new HomeFragment1();
            transaction.add(R.id.framLayout, fragment1, TAG_FRAGMENT_1);
        }
        if (fragment2 == null) {
            fragment2 = new HomeFragment2();
            transaction.add(R.id.framLayout, fragment2, TAG_FRAGMENT_2);
        }
        if (fragment3 == null) {
            fragment3 = new HomeFragment3();
            transaction.add(R.id.framLayout, fragment3, TAG_FRAGMENT_3);
        }
        transaction.show(fragment1.showThis()).hide(fragment2.hideThis()).hide(fragment3.hideThis()).commit();
    }


    public void reQuesetSucceed(List<HomeFragmentTitleBean> model) {
        Logger.i("请求成功");
        Logger.i("title大小:" + model.size());
        Logger.i(JSON.toJSONString(model));
    }


    @Override
    public void itemClick(String menuId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (menuId) {
            case TAG_FRAGMENT_1:
                transaction.show(fragment1.showThis()).hide(fragment2.hideThis()).hide(fragment3.hideThis());
                break;
            case TAG_FRAGMENT_2:
                transaction.show(fragment2.showThis()).hide(fragment1.hideThis()).hide(fragment3.hideThis());
                break;
            case TAG_FRAGMENT_3:
                transaction.show(fragment3.showThis()).hide(fragment2.hideThis()).hide(fragment1.hideThis());
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFRESH_UI) {
            //判断是否是从其他页面返回 刷新正在显示的fragment
            for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
                if (fragmentManager.getFragments().get(i).isVisible()) {
                    BaseDataBindingFragment fragment = (BaseDataBindingFragment) fragmentManager.getFragments().get(i);
                    fragment.backShowFragment();
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        isOtherJump();
    }

    /**
     * 判断是否是从其他地方跳到首页
     */
    public void isOtherJump() {
        int page = S.getI(MAIN_TAG_KEY);
        boolean isRefresh = S.getB(MAIN_TAG_REFRESH, false);
        switch (page) {
            case HOME_FRAGMENT_1:
                binding.buttonToolBar.setSelectPostion(0);
                if (isRefresh) {
                    fragment1.backShowFragment();
                }
                break;
            case HOME_FRAGMENT_2:
                binding.buttonToolBar.setSelectPostion(1);
                if (isRefresh) {
                    fragment2.backShowFragment();
                }
                break;
            case HOME_FRAGMENT_3:
                binding.buttonToolBar.setSelectPostion(2);
                if (isRefresh) {
                    fragment3.backShowFragment();
                }
                break;
            default:
                break;
        }

        if (isRefresh) {
            // setUserheader();
        }
        S.setI(MAIN_TAG_KEY, -1);
        S.setB(MAIN_TAG_REFRESH, false);
    }


    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showWarningToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                AppManager.getAppManager().AppExit(this);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void applyPermission() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    /**
     * 申请权限被拒绝
     */
    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionDenied() {
        showWarningToast("为了保证APP的正常使用，请您授予相关权限");
    }

    /**
     * 申请权限被设置为不再询问
     */
    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onNeverAskAgain() {
        showWarningToast("为了保证APP的正常使用，请您进设置中授予相关权限");
    }


}
