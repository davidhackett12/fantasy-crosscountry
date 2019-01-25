package com.fantasycrosscountry.fantasycrosscountry.models;


import javax.persistence.*;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    private User user;

    @ManyToOne
    private League league;

    @OneToMany
    @JoinColumn(name = "team_id")
    private List<Runner> runners;


    @OneToMany
    @JoinColumn(name = "team_id")
    private List<Performance> performances;

    @OneToMany
    @JoinColumn(name = "team_id")
    private List<TeamScore> teamScores;

    @OneToOne(mappedBy = "team")
    private OverallScore overallScore;

    @OneToMany
    @JoinColumn(name = "team_id")
    private List<Lineup> lineups;

    @OneToMany
    @JoinColumn(name = "proposer_id")
    private List<TradeProposal> tradesProposedBy;

    @OneToMany
    @JoinColumn(name = "proposee_id")
    private List<TradeProposal> tradesProposedTo;

    public Team() {
    }

    public Team(String name) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public List<Runner> getRunners() {
        return runners;
    }

    public void setRunners(List<Runner> runners) {
        this.runners = runners;
    }


    public List<TeamScore> getTeamScores() {
        return teamScores;
    }

    public void setTeamScores(List<TeamScore> teamScores) {
        this.teamScores = teamScores;
    }


    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public OverallScore getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(OverallScore overallScore) {
        this.overallScore = overallScore;
    }

    public List<Lineup> getLineups() {
        return lineups;
    }

    public void setLineups(List<Lineup> lineups) {
        this.lineups = lineups;
    }

    public List<TradeProposal> getTradesProposedBy() {
        return tradesProposedBy;
    }

    public void setTradesProposedBy(List<TradeProposal> tradesProposedBy) {
        this.tradesProposedBy = tradesProposedBy;
    }

    public List<TradeProposal> getTradesProposedTo() {
        return tradesProposedTo;
    }

    public void setTradesProposedTo(List<TradeProposal> tradesProposedTo) {
        this.tradesProposedTo = tradesProposedTo;
    }
}
