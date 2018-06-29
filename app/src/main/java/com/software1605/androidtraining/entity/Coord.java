package com.software1605.androidtraining.entity;

public class Coord {
    private int id;
    private double longitude;
    private double latitude;
    private String name;
    private int userId;
    private String imgId;

    public Coord(int id, double longitude, double latitude, String name, int userId,String imgId) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", imgId='" + imgId + '\'' +
                '}';
    }
}
