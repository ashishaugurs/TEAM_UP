

package com.teamup.model;

public class Event {

private String dateTime;
private String description;
private Boolean isCollectMoney;
private Boolean isSendAttendance;
private String lat;
private String location;
private String lng;
private String opponent;
private Boolean isRecurring;
private String type;
private String createdBy;
private Boolean isFavourite;
    private String goingStatus;
    private Boolean isDeleted;
    private String teamID, eventID;


    public void setGoingStatus(String goingStatus) {
        this.goingStatus = goingStatus;
    }


    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCollectMoney() {
        return isCollectMoney;
    }

    public void setCollectMoney(Boolean collectMoney) {
        isCollectMoney = collectMoney;
    }

    public Boolean getSendAttendance() {
        return isSendAttendance;
    }

    public void setSendAttendance(Boolean sendAttendance) {
        isSendAttendance = sendAttendance;
    }

    public String getLat() {
        return lat;
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
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public Boolean getRecurring() {
        return isRecurring;
    }

    public void setRecurring(Boolean recurring) {
        isRecurring = recurring;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public Event() {
    }

    public Event(String dateTime, String description, Boolean isCollectMoney, Boolean isSendAttendance, String lat, String location, String lng, String opponent, Boolean isRecurring, String type, String createdBy, Boolean isFavourite, String goingStatus, Boolean isDeleted, String teamID, String eventID) {
        this.dateTime = dateTime;
        this.description = description;
        this.isCollectMoney = isCollectMoney;
        this.isSendAttendance = isSendAttendance;
        this.lat = lat;
        this.location = location;
        this.lng = lng;
        this.opponent = opponent;
        this.isRecurring = isRecurring;
        this.type = type;
        this.createdBy = createdBy;
        this.isFavourite = isFavourite;
        this.goingStatus = goingStatus;
        this.isDeleted = isDeleted;
        this.teamID = teamID;
        this.eventID = eventID;
    }


    public String getGoingStatus() {
        return goingStatus;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}