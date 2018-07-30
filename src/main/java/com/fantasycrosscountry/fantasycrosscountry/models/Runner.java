package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Runner {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String highSchool;

    @ManyToOne
    private Team team;

    @ManyToOne
    private League league;

    public Runner() {
    }

    public Runner(String name, String highSchool, Team team) {
        this.name = name;
        this.highSchool = highSchool;
        this.team = team;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(String highSchool) {
        this.highSchool = highSchool;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
