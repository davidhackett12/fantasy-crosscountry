package com.fantasycrosscountry.fantasycrosscountry.models;

import com.fantasycrosscountry.fantasycrosscountry.models.comparators.PerformanceComparator;
import com.fantasycrosscountry.fantasycrosscountry.models.comparators.TeamScoreComparator;
import com.fantasycrosscountry.fantasycrosscountry.models.data.PerformanceDao;
import com.fantasycrosscountry.fantasycrosscountry.models.data.TeamScoreDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Entity
public class Race {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    private League league;

    @OneToMany
    @JoinColumn(name = "race_id")
    private List<Performance> performances;

    @OneToMany
    @JoinColumn(name = "race_id")
    private List<TeamScore> teamScores;

    @OneToMany
    @JoinColumn(name = "race_id")
    private List<Lineup> lineups;

    private Calendar startDate;

    private Calendar endDate;

    private Boolean currentRace;


    public Race() {
    }

    public Race(String name) {
        this.name = name;
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

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public List<Performance> getPerformances() {
        PerformanceComparator performanceComparator = new PerformanceComparator();
        performances.sort(performanceComparator);
        return performances;
    }

    public void setPerformances(List<Performance> performances) {

        this.performances = performances;
    }

    public List<TeamScore> getTeamScores() {
        TeamScoreComparator teamScoreComparator = new TeamScoreComparator();
        teamScores.sort(teamScoreComparator);
        return teamScores;
    }

    public void setTeamScores(List<TeamScore> teamScores) {
        this.teamScores = teamScores;
    }

    public List<Lineup> getLineups() {
        return lineups;
    }

    public void setLineups(List<Lineup> lineups) {
        this.lineups = lineups;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Boolean getCurrentRace() {
        return currentRace;
    }

    public void setCurrentRace(Boolean currentRace) {
        this.currentRace = currentRace;
    }
}
