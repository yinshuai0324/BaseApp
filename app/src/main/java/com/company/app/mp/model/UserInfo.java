package com.company.app.mp.model;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户信息实体类
 * @author yinshuai
 */
public class UserInfo implements Serializable{
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String userNick;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 用户性别
     */
    private int userSex = 0;

    /**
     * 用户描述
     */
    private String userDesc;

    /**
     * 用户头像
     */
    private String userHeader;

    /**
     * 用户推送id
     */
    private String userPushId;

    /**
     * 用户token
     */
    private String userToken;

    /**
     * 用户创建时间
     */
    private Date userCreateTime;

    /**
     * 用户最后接收到消息的时间
     */
    private Date userLastReceivedTime;

    /**
     * 用户更新信息的时间
     */
    private Date userUpdateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public String getUserPushId() {
        return userPushId;
    }

    public void setUserPushId(String userPushId) {
        this.userPushId = userPushId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public Date getUserLastReceivedTime() {
        return userLastReceivedTime;
    }

    public void setUserLastReceivedTime(Date userLastReceivedTime) {
        this.userLastReceivedTime = userLastReceivedTime;
    }

    public Date getUserUpdateTime() {
        return userUpdateTime;
    }

    public void setUserUpdateTime(Date userUpdateTime) {
        this.userUpdateTime = userUpdateTime;
    }
}
