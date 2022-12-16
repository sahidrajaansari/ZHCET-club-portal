package com.example.zcm.model;

public class ModelClub {
    String clubname,clubid,email,pass,profileimage,status,user;
    ModelClub(){

    }

    public ModelClub(String clubname, String clubid, String email, String pass, String profileimage, String status, String user) {
        this.clubname = clubname;
        this.clubid = clubid;
        this.email = email;
        this.pass = pass;
        this.profileimage = profileimage;
        this.status = status;
        this.user = user;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getClubid() {
        return clubid;
    }

    public void setClubid(String clubid) {
        this.clubid = clubid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
