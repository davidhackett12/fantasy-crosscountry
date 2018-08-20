package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class League {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    private User commissioner;

    @ManyToMany(mappedBy = "leagues")
    private List<User> users;

    @OneToMany
    @JoinColumn(name = "league_id")
    private List<Team> teams;

    @OneToMany
    @JoinColumn(name = "league_id")
    private List<Runner> runners;

    @OneToMany
    @JoinColumn(name = "league_id")
    private List<Race> races;

    public League() {
    }

    public League(String name) {
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

    public User getCommissioner() {
        return commissioner;
    }

    public void setCommissioner(User commissioner) {
        this.commissioner = commissioner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Runner> getRunners() {
        return runners;
    }

    public void setRunners(List<Runner> runners) {
        this.runners = runners;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }
}
