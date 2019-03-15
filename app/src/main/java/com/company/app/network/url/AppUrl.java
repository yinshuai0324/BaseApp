package com.company.app.network.url;

/**
 * @author yinshuai
 * @date 2018/4/20
 */

public interface AppUrl {

    /**
     * 用户注册 用户名
     */
    String USER_REGISTER = "user/register";

    /**
     * 用户登陆
     */
    String USER_LOGIN = "user/login";

    /**
     * 更新用户头像
     */
    String USER_UPDATA_HEADER = "user/upDateUserHeader";

    /**
     * 更新用户基本信息
     */
    String USER_UPDATA_BASE = "user/upDateUserBaseInfo";

    /**
     * 更新用户信息
     */
    String USER_UPDATA_INFO = "user/updata";

    /**
     * 添加好友
     */
    String ADD_FRIEND = "friend/addFriend";

    /**
     * 查询好友列表
     */
    String QUERY_FRIEND_LIST = "friend/queryFriendList";


    /**
     * 查询首页消息列表用户信息
     */
    String QUERY_MESSAGE_LIST_INFO = "user/queryMessageListInfo";

    /**
     * 发送消息
     */
    String SENDER_MESSAGE = "message/sendMessage";
}
