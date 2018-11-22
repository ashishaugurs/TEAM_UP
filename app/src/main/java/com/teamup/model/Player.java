package com.teamup.model;


import java.io.Serializable;

public class Player implements Serializable {

    private String createdOn;
    private String describe;
    private String deviceId;
    private String dob;
    private String email;
    private Boolean emailNotification;
    private String firstname;
    private String gender;
    private String imageUrl;
    private String lastname;
    private String lat;
    private String location;
    private String lng;
    private String modifiedOn;
    private String phone;
    private String pushNotification;
    private String status;
    private Boolean textNotification;
    private String sa;


    public String getCreatedOn() {
        return createdOn==null?"":createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getDescribe() {
        return describe==null?"":describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDeviceId() {
        return deviceId==null?"":deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDob() {
        return dob==null?"":dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email==null?"":email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailNotification() {
        return emailNotification!=null;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public String getFirstname() {
        return firstname==null?"":firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender==null?"":gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl==null?"":imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLat() {
        return lat==null?"":lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLocation() {
        return location==null?"":location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getlng() {
        return lng ==null?"": lng;
    }

    public void setlng(String lng) {
        this.lng = lng;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPushNotification() {
        return pushNotification==null?"":pushNotification;
    }

    public void setPushNotification(String pushNotification) {
        this.pushNotification = pushNotification;
    }

    public String getStatus() {
        return status==null?"":status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getTextNotification() {
        return textNotification!=null;
    }

    public void setTextNotification(Boolean textNotification) {
        this.textNotification = textNotification;
    }

    public String getSa() {
        return sa ==null?"": sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }



}