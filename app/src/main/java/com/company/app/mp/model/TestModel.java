package com.company.app.mp.model;

/**
 * 描述:
 * 创建时间：2018/11/1-1:38 PM
 *
 * @author: yinshuai
 */
public class TestModel {

    public static class UserModel {
        public String User_GUID;
        public String User_Code;
        public String User_Name;
        public String CreateDate;
        public String Creator;
        public String User_Password;
        public String User_State;


        public String getUser_GUID() {
            return User_GUID;
        }

        public void setUser_GUID(String user_GUID) {
            User_GUID = user_GUID;
        }

        public String getUser_Code() {
            return User_Code;
        }

        public void setUser_Code(String user_Code) {
            User_Code = user_Code;
        }

        public String getUser_Name() {
            return User_Name;
        }

        public void setUser_Name(String user_Name) {
            User_Name = user_Name;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }

        public String getCreator() {
            return Creator;
        }

        public void setCreator(String creator) {
            Creator = creator;
        }

        public String getUser_Password() {
            return User_Password;
        }

        public void setUser_Password(String user_Password) {
            User_Password = user_Password;
        }

        public String getUser_State() {
            return User_State;
        }

        public void setUser_State(String user_State) {
            User_State = user_State;
        }
    }

}
