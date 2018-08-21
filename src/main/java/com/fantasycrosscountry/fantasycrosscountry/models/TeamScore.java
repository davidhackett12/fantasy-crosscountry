package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TeamScore extends Score {

    @Id
    @GeneratedValue
    private int id;

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

}
