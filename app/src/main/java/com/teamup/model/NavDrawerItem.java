package com.teamup.model;

public class NavDrawerItem {

    private boolean showNotify;
    private String title;
    private int picItem;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int picItem) {
        this.showNotify = showNotify;
        this.title = title;
        this.picItem = picItem;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPicItem() {
        return picItem;
    }

    public void setPicItem(int picItem) {
        this.picItem = picItem;
    }


}