package com.example.appcon;

public class peerModel {
    private String id,peer_username, cheer_points, peer_medal,peer_profile;

    public peerModel(String id,String peer_username, String cheer_points, String peer_medal, String peer_profile) {
       this.id=id;
        this.peer_username = peer_username;
        this.cheer_points = cheer_points;
        this.peer_medal = peer_medal;
        this.peer_profile=peer_profile;
    }
    public String getId(){
        return id;
    }
    public String getPeer_profile() {
        return peer_profile;
    }
    public String getPeer_username() {
        return peer_username;
    }

    public void setPeer_username(String peer_username) {
        this.peer_username = peer_username;
    }

    public String getCheer_points() {
        return cheer_points;
    }

    public void setCheer_points(String cheer_points) {
        this.cheer_points = cheer_points;
    }

    public String getPeer_medal() {
        return peer_medal;
    }

    public void setPeer_medal(String peer_medal) {
        this.peer_medal = peer_medal;
    }

}

