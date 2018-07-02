package com.software1605.androidtraining.staticDate;

import com.software1605.androidtraining.entity.User;

//存储用户数据
public class UserInfo {
    private static UserInfo _userInfo;
    private User user;
    public UserInfo() {

    }

    public static UserInfo getUserInfo(){
        if (_userInfo == null){
            _userInfo = new UserInfo();
        }
        return _userInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
