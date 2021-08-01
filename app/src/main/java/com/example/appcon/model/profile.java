package com.example.appcon.model;

public class profile {
private String fname,lname,dob,gender,phone_no,bio,profile;

    public profile() {

    }
    public profile(String fname, String lname, String dob, String gender, String phone_no, String bio, String profile) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.gender = gender;
        this.phone_no = phone_no;
        this.bio = bio;
        this.profile = profile;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
