package com.example.appcon.model;

public class user {
    private String id;
    private String name;
    private String Cheerpoints;
    private String Medal;
    private String email;
    private  String user_profile;
    private String app_id;


    private String status;
    public user(){
        this.id = "0";
        this.name = "0";
        Cheerpoints = "0";
        Medal = "0";
        this.email = "0";
        this.app_id="";
        this.user_profile="default";
        this.status="";
    }

    public user(String id, String name, String cheerpoints, String medal, String email, String app_id, String user_profile, String status) {
        this.id = id;
        this.name = name;
        Cheerpoints = cheerpoints;
        Medal = medal;
        this.email = email;
        this.app_id=app_id;
        this.user_profile=user_profile;
        this.status=status;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheerpoints() {
        return Cheerpoints;
    }

    public void setCheerpoints(String cheerpoints) {
        Cheerpoints = cheerpoints;
    }

    public String getMedal() {
        return Medal;
    }

    public void setMedal(String medal) {
        Medal = medal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
