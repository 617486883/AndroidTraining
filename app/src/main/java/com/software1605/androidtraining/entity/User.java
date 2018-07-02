package com.software1605.androidtraining.entity;

import java.io.Serializable;

public class User implements Serializable{
    private int state;
    private String stateInfo;
    private String name;
   private String password;
   private String userName;
   private String type;
    private String imgUrl;

    public User() {

    }

    public User(int state, String stateInfo, String name, String password, String userName, String type, String imgUrl) {
        this.state = state;
        this.stateInfo = stateInfo;
        this.name = name;
        this.password = password;
        this.userName = userName;
        this.type = type;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", type='" + type + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
