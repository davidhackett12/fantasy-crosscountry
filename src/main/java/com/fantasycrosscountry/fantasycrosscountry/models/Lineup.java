package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Lineup {

    @Id
    @GeneratedValue
    private int id;

    @ManyToMany(mappedBy = "lineups")
    private List<Runner> runners;

    @ManyToOne
    private Race race;

    @ManyToOne
    private Team team;

    public Lineup() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Runner> getRunners() {
        return runners;
    }

    public void setRunners(List<Runner> runners) {
        this.runners = runners;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
