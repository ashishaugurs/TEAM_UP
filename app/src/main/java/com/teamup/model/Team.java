
package com.teamup.model;

public class Team {

    private String ageGroup;
    private String createdBy;
    private String createdOn;
    private String gender;
    private String lat;
    private String location;
    private String lng;
    private String modifiedOn;
    private String name;
    private String status;
    private String sa;


    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLat() {
        return lat==null?"":lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLng() {
        return lng==null?"":lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getModifiedOn() {
        return modifiedOn==null?"":modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status==null?"":status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSa() {
        return sa==null?"":sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }

}