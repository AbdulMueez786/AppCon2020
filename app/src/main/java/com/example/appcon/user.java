package com.example.appcon;

public class user {
    private String id;
    private String name;
    private String Cheerpoints;
    private String Medal;
    private String email;
    public user(){
        this.id = "0";
        this.name = "0";
        Cheerpoints = "0";
        Medal = "0";
        this.email = "0";
    }
    public user(String id, String name, String cheerpoints, String medal, String email) {
        this.id = id;
        this.name = name;
        Cheerpoints = cheerpoints;
        Medal = medal;
        this.email = email;
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
