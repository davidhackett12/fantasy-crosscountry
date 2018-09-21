package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.*;
import java.time.Duration;
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
    private League league;

    @OneToMany
    @JoinColumn(name = "runner_id")
    private List<Performance> performanceList;

    @ManyToMany
    private List<Lineup> lineups;

    private String personalBest;

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


    public List<Performance> getPerformanceList() {
        return performanceList;
    }

    public void setPerformanceList(List<Performance> performanceList) {
        this.performanceList = performanceList;
    }

    public List<Lineup> getLineups() {
        return lineups;
    }

    public void setLineups(List<Lineup> lineups) {
        this.lineups = lineups;
    }

    public void addLineup(Lineup lineup){
        this.lineups.add(lineup);
    }

    public void removeLineup(Lineup lineup){
        this.lineups.remove(lineup);
    }

    public String getPersonalBest() {
        return personalBest;
    }

    public void setPersonalBest() {
        Performance currentBest = new Performance("99","99","99");
        for (Performance performance : performanceList){
            if (performance.getTime().compareTo(currentBest.getTime()) < 0){
                currentBest = performance;
            }
        }

        this.personalBest = currentBest.toString();
    }
}
