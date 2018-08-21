package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.*;

@Entity
public class OverallScore {

    @Id
    @GeneratedValue
    private int id;

    private int score;

    private int place;


    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    private League league;

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

    public void updateScore(){
        int newScore = 0;
        for (TeamScore teamScore : team.getTeamScores()){
            newScore =+ teamScore.getScore();
        }

        this.setScore(newScore);
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
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
