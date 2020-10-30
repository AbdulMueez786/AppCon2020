package com.example.appcon;

public class post {
    private String title;
    private String sender_id;
    private String receiver_id;
    private String cheer_type;
    private String description;
    public post() {
        this.title = "";
        this.sender_id = "";
        this.receiver_id = "";
        this.cheer_type = "";
        this.description ="";
    }
    public post(String title, String sender_id, String receiver_id, String cheer_type, String description) {
        this.title = title;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.cheer_type = cheer_type;
        this.description = description;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getCheer_type() {
        return cheer_type;
    }

    public void setCheer_type(String cheer_type) {
        this.cheer_type = cheer_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }







}

