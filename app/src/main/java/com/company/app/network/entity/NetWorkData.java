package com.company.app.network.entity;

import java.io.Serializable;

/**
 * Created by yinshuai on 2018/4/18.
 * @author yinshuai
 */

public class NetWorkData<T> implements Serializable{
    private boolean succeed;
    private String message;
    private int code;
    private T data;



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
