package com.teamup.model;

public class NotificationModel {

    String title,content,type;
    int img;

    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
     return title;
    }

    public void setContent(String content){
    this.content=content;
    }
    public String getContent(){
        return content;
    }

    public void setImg(int img){
        this.img=img;
    }
    public int getImg(){
        return img;
    }

    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }

}
