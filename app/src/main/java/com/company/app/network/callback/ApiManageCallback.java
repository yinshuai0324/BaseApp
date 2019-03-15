package com.company.app.network.callback;


import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.app.network.config.JsonKey;
import com.company.app.network.entity.NetWorkData;
import com.company.app.network.error.ErroeCode;
import com.company.app.network.error.Errorinfo;
import com.company.app.network.interfaces.RequestCallback;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yinshuai
 * @date 2018/4/18
 * 数据包解析类
 */

public abstract class ApiManageCallback<T> implements RequestCallback, ErroeCode, JsonKey {

    /**
     * 解析网络数据
     *
     * @param json
     * @return
     */
    @Override
    public void convertResponse(final String json) {
        Logger.json(json);
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                //在子线程中做数据解析操作
                JSONObject jsonObject = JSONObject.parseObject(json);
                if (jsonObject != null) {
                    String message = "";
                    boolean succeed = false;
                    int code = -1;
                    String jsonData = null;

                    if (jsonObject.containsKey(KEY_MESSAGE)) {
                        message = jsonObject.getString(KEY_MESSAGE);
                    }
                    if (jsonObject.containsKey(KEY_SUCCEED)) {
                        succeed = jsonObject.getBoolean(KEY_SUCCEED);
                    }
                    if (jsonObject.containsKey(KEY_CODE)) {
                        code = jsonObject.getInteger(KEY_CODE);
                    }

                    //判断token是否已经过期
//            if (code == CODE_TOKEN_LOSE) {
//                JumpActivity.jump((Activity) context, JumpAction.ACTIVITY_LOGIN);
//                return;
//            }

                    //解析data数据
                    if (jsonObject.containsKey(KEY_DATA)) {
                        NetWorkData netWorkData = new NetWorkData<>();
                        if (!TextUtils.isEmpty(jsonObject.get(KEY_DATA).toString())) {
                            jsonData = jsonObject.get(KEY_DATA).toString();
                            try {
                                if (jsonData == null || TextUtils.isEmpty(jsonData)) {
                                    netWorkData.setSucceed(succeed);
                                    netWorkData.setMessage(message);
                                    netWorkData.setCode(code);
                                    netWorkData.setData(null);
                                } else {
                                    //解析泛型数据
                                    T data = JSON.parseObject(jsonData, getType(ApiManageCallback.this));
                                    netWorkData.setData(data);
                                    netWorkData.setSucceed(succeed);
                                    netWorkData.setMessage(message);
                                    netWorkData.setCode(code);
                                }

                                emitter.onNext(netWorkData);
                            } catch (Exception e) {
                                Logger.e(e.getMessage());
                                emitter.onNext(new Errorinfo().setMessage("数据解析异常").setCode(ANALYSIS_DATA_DATA_FAILUERE));
                            }
                        } else {
                            netWorkData.setSucceed(succeed);
                            netWorkData.setMessage(message);
                            netWorkData.setCode(code);
                            netWorkData.setData(null);
                            emitter.onNext(netWorkData);
//                            onSucceed(netWorkData);
                        }
                    }
                } else {
                    emitter.onNext(new Errorinfo().setMessage("解析数据失败").setCode(ANALYSIS_DATA_FAILUERE));
                }
            }
        })
                .subscribeOn(Schedulers.io())
                //切换到UI线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        //回调给View层更新
                        if (o instanceof NetWorkData) {
                            onSucceed((NetWorkData<T>) o);
                        } else if (o instanceof Errorinfo) {
                            onFailure((Errorinfo) o);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailure(new Errorinfo().setMessage(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    /**
     * 请求成功
     *
     * @param data
     */
    @Override
    public void requestSuccess(String data) {
        if (!TextUtils.isEmpty(data)) {
            convertResponse(data);
        } else {
            onFailure(new Errorinfo().setMessage("服务器返回数据为空").setCode(SERVICE_DATA_EMPTY));
        }
    }

    @Override
    public void requestError(Exception error) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext(error);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
            @Override
            public void onNext(Object o) {
                onFailure(new Errorinfo().setError((Exception) o).setCode(OKHTTP_ERROR));
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }
        });
    }

    /**
     * 使用反射获取泛型<T>的Class<T>
     *
     * @return
     */
    protected <T> Type getType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) {
                throw new IllegalStateException("没有填写泛型参数");
            }
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            finalNeedType = type;
        }
        return finalNeedType;
    }

    /**
     * 成功回调
     *
     * @param result
     */
    protected abstract void onSucceed(NetWorkData<T> result);

    /**
     * 失败回调
     *
     * @param error
     */
    protected abstract void onFailure(Errorinfo error);
}
