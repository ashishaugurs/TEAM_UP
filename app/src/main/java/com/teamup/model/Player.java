package com.teamup.model;


import java.io.Serializable;

public class Player implements Serializable {

    private String created_on;
    private String describe;
    private String device_id;
    private String dob;
    private String email;
    private Boolean email_notification;
    private String firstname;
    private String gender;
    private String image_url;
    private String lastname;
    private String lat;
    private String location;
    private String lng;
    private String modified_on;
    private String phone;
    private String push_notification;
    private String status;
    private Boolean text_notification;
    private String sa;


    public String getCreated_on() {
        return created_on ==null?"": created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getDescribe() {
        return describe==null?"":describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDevice_id() {
        return device_id ==null?"": device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
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

    public Boolean getEmail_notification() {
        return email_notification !=null;
    }

    public void setEmail_notification(Boolean email_notification) {
        this.email_notification = email_notification;
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

    public String getImage_url() {
        return image_url ==null?"": image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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

    public String getModified_on() {
        return modified_on;
    }

    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPush_notification() {
        return push_notification ==null?"": push_notification;
    }

    public void setPush_notification(String push_notification) {
        this.push_notification = push_notification;
    }

    public String getStatus() {
        return status==null?"":status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getText_notification() {
        return text_notification !=null;
    }

    public void setText_notification(Boolean text_notification) {
        this.text_notification = text_notification;
    }

    public String getSa() {
        return sa ==null?"": sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }



}