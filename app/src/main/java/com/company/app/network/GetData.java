package com.company.app.network;

import com.company.app.network.callback.ApiManageCallback;
import com.company.app.network.config.NetConfig;
import com.company.app.network.config.NetWorkConfig;
import com.company.app.network.entity.Method;
import com.company.app.network.entity.RequestParams;
import com.company.app.network.okhttp.OkHttpManage;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author yinshuai
 * @date 2018/4/18
 * 网络请求工具
 */

public class GetData implements NetWorkConfig, Method {

    /**
     * get请求
     */
    public static <T> void GetRequest(String url, final ApiManageCallback<T> callback) {
        requestNetWork(GET, url, new RequestParams(), callback);
    }

    /**
     * post请求
     */
    public static <T> void PostRequest(String url, final RequestParams params, ApiManageCallback<T> callback) {
        requestNetWork(POST, url, params, callback);
    }


    /**
     * @param method   请求方式
     * @param url      请求地址
     * @param params   参数
     * @param callback 回调
     * @param <T>      泛型
     */
    private static <T> void requestNetWork(int method, String url, final RequestParams params, ApiManageCallback<T> callback) {
        String requestUrl = new StringBuffer(NetConfig.baseUrl).append(url).toString();
        Logger.d("请求url:" + requestUrl + "  请求参数:" + params.getValue().toString());

        Request.Builder requestBulid = new Request.Builder();
        requestBulid.url(requestUrl);

        Request request = null;
        if (method == POST) {
            RequestBody body = RequestBody.create(OkHttpManage.getInstance().TYPE_JSON, params.toJson());
            requestBulid.post(body);
            request = requestBulid.build();
        } else {
            request = new Request.Builder().url(requestUrl).build();
        }

        //开始请求
        Call call = OkHttpManage.getInstance().getClient().newCall(request);
        call.enqueue(getCallback(callback));
    }


    /**
     * 请求回调
     *
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> Callback getCallback(final ApiManageCallback<T> callback) {
        Callback responseCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.requestError(e);
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (callback != null && response != null) {
                    callback.convertResponse(response.body().string());
                }
            }
        };

        return responseCallback;
    }

}
