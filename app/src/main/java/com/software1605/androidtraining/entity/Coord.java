package com.software1605.androidtraining.entity;

public class Coord {
    private int id;
    private double longitude;
    private double latitude;
    private String name;
    private String  userName;
    private String imgId;

    public Coord(int id, double longitude, double latitude, String name, String userName,String imgId) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.userName = userName;
        this.imgId = imgId;
    }


    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userName;
    }

    public void setUserId(String userId) {
        this.userName = userId;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", name='" + name + '\'' +
                ", userName=" + userName +
                ", imgId='" + imgId + '\'' +
                '}';
    }
}
