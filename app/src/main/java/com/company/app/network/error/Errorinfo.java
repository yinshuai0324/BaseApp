package com.company.app.network.error;


/**
 * Created by yinshuai on 2018/4/19.
 */

public class Errorinfo {
    private int code;
    private Exception error;
    private String message = "";


    public int getCode() {
        return code;
    }

    public Errorinfo setCode(int code) {
        this.code = code;
        return this;
    }

    public Exception getError() {
        return error;
    }

    public Errorinfo setError(Exception error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Errorinfo setMessage(String message) {
        this.message = message;
        return this;
    }

}
