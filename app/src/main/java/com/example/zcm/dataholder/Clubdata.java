
package com.example.zcm.dataholder;

public class Clubdata {
    String user,clubname,pass,clubprovelink,email,status,profileimage,clubid,clubdescription;

    public Clubdata(String user, String clubname, String pass, String clubprovelink, String email, String status, String profileimage, String clubid, String clubdescription) {
        this.user = user;
        this.clubname = clubname;
        this.pass = pass;
        this.clubprovelink = clubprovelink;
        this.email = email;
        this.status = status;
        this.profileimage = profileimage;
        this.clubid = clubid;
        this.clubdescription = clubdescription;
    }


    public String getClubdescription() {
        return clubdescription;
    }

    public void setClubdescription(String clubdescription) {
        this.clubdescription = clubdescription;
    }

    public Clubdata() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getClubprovelink() {
        return clubprovelink;
    }

    public void setClubprovelink(String clubprovelink) {
        this.clubprovelink = clubprovelink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getClubid() {
        return clubid;
    }

    public void setClubid(String clubid) {
        this.clubid = clubid;
    }
}

