package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TeamScore {

    @Id
    @GeneratedValue
    private int id;

    private int score;

    private int place;


    @ManyToOne
    private Team team;

    @ManyToOne
    private Race race;

    public TeamScore() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

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
