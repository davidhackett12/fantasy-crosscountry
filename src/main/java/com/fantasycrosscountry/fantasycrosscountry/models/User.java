package com.fantasycrosscountry.fantasycrosscountry.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 5, max = 20)
    private String username;

    @Email
    private String email;

    @NotNull
    @Size(min = 5, max = 20)
    private String password;

    @ManyToMany
    @JoinColumn(name= "commissioner_id")
    private List<League> commissionedLeagues;

    @ManyToMany
    private List<League> leagues;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Team> teams;


    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<League> getCommissionedLeagues() {
        return commissionedLeagues;
    }

    public void setCommissionedLeagues(List<League> commissionedLeagues) {
        this.commissionedLeagues = commissionedLeagues;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void addLeague(League newLeague){
        leagues.add(newLeague);
    }
}




