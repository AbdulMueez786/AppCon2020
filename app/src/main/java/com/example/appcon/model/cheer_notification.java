package com.example.appcon.model;

public class cheer_notification {
    String notification_title;
    String reciever_id;
    String sender_id;
    String time;
    String cheer_type;
    String sender_image;
    String notification_status;

    public cheer_notification(){

    }

    public cheer_notification(String notification_title, String reciever_id, String sender_id
            , String time, String cheer_type, String sender_image, String notification_status) {
        this.notification_title = notification_title;
        this.reciever_id = reciever_id;
        this.sender_id = sender_id;
        this.time = time;
        this.cheer_type = cheer_type;
        this.sender_image = sender_image;
        this.notification_status=notification_status;
    }

    public String getNotification_status() {
        return notification_status;
    }

    public void setNotification_status(String notification_status) {
        this.notification_status = notification_status;
    }

    public String getNotification_title() {
        return notification_title;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public String getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(String reciever_id) {
        this.reciever_id = reciever_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCheer_type() {
        return cheer_type;
    }

    public void setCheer_type(String cheer_type) {
        this.cheer_type = cheer_type;
    }

    public String getSender_image() {
        return sender_image;
    }

    public void setSender_image(String sender_image) {
        this.sender_image = sender_image;
    }

}
