package com.example.zcm.dataholder;

public class StudentData {
    String name,number,Uid;

    public StudentData() {
    }

    public StudentData(String name, String number, String Uid) {
        this.name = name;
        this.number = number;
        this.Uid = Uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String mail) {
        this.Uid = mail;
    }
}
