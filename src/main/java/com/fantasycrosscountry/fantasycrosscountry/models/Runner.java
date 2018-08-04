package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.*;
import java.util.List;

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
    private Team lineup;

    @ManyToOne
    private League league;

    @OneToMany
    @JoinColumn(name = "runner_id")
    private List<Performance> performanceList;

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

    public Team getLineup() {
        return lineup;
    }

    public void setLineup(Team lineup) {
        this.lineup = lineup;
    }


    public List<Performance> getPerformanceList() {
        return performanceList;
    }

    public void setPerformanceList(List<Performance> performanceList) {
        this.performanceList = performanceList;
    }
}
