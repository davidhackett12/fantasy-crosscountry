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
}
