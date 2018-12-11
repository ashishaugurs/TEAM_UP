package com.teamup.model;

public class Member {

    private String teamID;
    private String phone;
    private String firstname;
    private String lastname;
    private String gender;
    private String group;

    private String uid;
    private Boolean hasRequestAccepted;
    private Boolean isAdmin;

    public Member() {
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getfirstname() {
        return firstname;
    }

    public void setfirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getlastname() {
        return lastname;
    }

    public void setlastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Boolean getHasRequestAccepted() {
        return hasRequestAccepted;
    }

    public void setHasRequestAccepted(Boolean hasRequestAccepted) {
        this.hasRequestAccepted = hasRequestAccepted;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }


    public Member(String teamID, String phone, String firstname, String lastname, String gender, String group, String uid, Boolean hasRequestAccepted, Boolean isAdmin) {
        this.teamID = teamID;
        this.phone = phone;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.group = group;
        this.uid = uid;
        this.hasRequestAccepted = hasRequestAccepted;
        this.isAdmin = isAdmin;
    }

    public String getTeamID() {
        return teamID;
    }
}
