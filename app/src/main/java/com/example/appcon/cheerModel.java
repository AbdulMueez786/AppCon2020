package com.example.appcon;
public class cheerModel {
    private String cheer_title, cheer_description, cheer_points, cheer_url;

    public cheerModel(String cheer_title, String cheer_description, String cheer_points, String cheer_url) {
        this.cheer_title = cheer_title;
        this.cheer_description = cheer_description;
        this.cheer_points = cheer_points;
        this.cheer_url = cheer_url;
    }

    public String getCheer_title() {
        return cheer_title;
    }

    public void setCheer_title(String cheer_title) {
        this.cheer_title = cheer_title;
    }

    public String getCheer_description() {
        return cheer_description;
    }

    public void setCheer_description(String cheer_description) {
        this.cheer_description = cheer_description;
    }

    public String getCheer_points() {
        return cheer_points;
    }

    public void setCheer_points(String cheer_points) {
        this.cheer_points = cheer_points;
    }

    public String getCheer_url() {
        return cheer_url;
    }

    public void setCheer_url(String cheer_url) {
        this.cheer_url = cheer_url;
    }
}

