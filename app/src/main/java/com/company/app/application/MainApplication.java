package com.company.app.application;


import com.company.app.global.S;
import com.company.app.network.config.NetConfig;
import com.company.app.network.utils.Res;
import com.lzy.okgo.OkGo;
import com.lzy.okserver.OkDownload;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @author yinshuai
 * @date 2018/4/17
 */

public class MainApplication extends BaseApplication {


    @Override
    protected void initSDK() {
        //初始化logcat
        Logger.addLogAdapter(new AndroidLogAdapter());
        //本地化初始化工具
        S.init(this);
        //资源初始化
        Res.iniRes(mContext);
        //okgo init
        OkGo.getInstance().init(this);
    }

    @Override
    protected void initActivity() {

    }

    @Override
    protected void initConfig() {
        NetConfig.initNetWorkConfig(this);
    }

    @Override
    protected void initVolley() {
    }


}
