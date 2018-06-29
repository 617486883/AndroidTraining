package com.software1605.androidtraining.dao;


import android.os.Handler;

//从网络获取到资源
public interface UserDao {

    public void checkLogin(String name , String password, Handler handler);

}
