package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.util.Date;

@Entity
public class Performance {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Race race;

    @ManyToOne
    private Runner runner;

    @ManyToOne
    private Team team;

    private String minutes;

    private String seconds;

    private String milliseconds;

    private Duration time;

    private int place;


    public Performance() {
    }
    public Performance(String minutes, String seconds, String milliseconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
        Long min = Long.parseLong(minutes);
        Long sec = Long.parseLong(seconds);
        Long milli = Long.parseLong(milliseconds);
        Long totalSeconds = (min*60) + sec + (milli/60);
        this.time = Duration.ofSeconds(totalSeconds);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(String milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Duration getTime() {
        return time;
    }

    public void setTime() {
        Long min = Long.parseLong(minutes);
        Long sec = Long.parseLong(seconds);
        Long milli = Long.parseLong(milliseconds);
        Long totalSeconds = (min*60) + sec + (milli/60);
        this.time = Duration.ofSeconds(totalSeconds);
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {

        return this.minutes +":"+this.seconds+"."+this.milliseconds;
    }

}
