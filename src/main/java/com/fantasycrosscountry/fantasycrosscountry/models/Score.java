package com.fantasycrosscountry.fantasycrosscountry.models;

public abstract class Score {

    private int score;

    private int place;

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
