package com.software1605.androidtraining.entity;

public class Text {
    private String content;
    private int id;
    private String imgUrl;
    private String title;
    private String userName;


    public Text() {
    }

    public Text(String content, int id, String imgUrl, String title, String userName) {
        this.content = content;
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Text{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
