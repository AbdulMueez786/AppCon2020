package com.example.appcon.model;

public class LeaderboardItem {
    String name;
    int cheerscount,position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public LeaderboardItem(String name, int cheerscount, int p, String id) {
        this.name = name;
        this.cheerscount = cheerscount;
        this.position=p;
        this.id=id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCheerscount() {
        return cheerscount;
    }

    public void setCheerscount(int cheerscount) {
        this.cheerscount = cheerscount;
    }
}

