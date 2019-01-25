package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class TradeProposal {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Team proposer;

    @ManyToOne
    private Team proposee;

    @OneToMany
    @JoinColumn(name = "trade_away_id")
    private List<Runner> proposerRunners;

    @OneToMany
    @JoinColumn(name = "trade_for_id")
    private List<Runner> proposeeRunners;

    @OneToMany
    @JoinColumn(name = "drop_id")
    private List<Runner> runnersToDrop;

    public TradeProposal() {
    }

    public TradeProposal(Team proposer) {
        this.proposer = proposer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getProposer() {
        return proposer;
    }

    public void setProposer(Team proposer) {
        this.proposer = proposer;
    }

    public Team getProposee() {
        return proposee;
    }

    public void setProposee(Team proposee) {
        this.proposee = proposee;
    }

    public List<Runner> getProposerRunners() {
        return proposerRunners;
    }

    public void setProposerRunners(List<Runner> proposerRunners) {
        this.proposerRunners = proposerRunners;
    }

    public List<Runner> getProposeeRunners() {
        return proposeeRunners;
    }

    public void setProposeeRunners(List<Runner> proposeeRunners) {
        this.proposeeRunners = proposeeRunners;
    }

    public List<Runner> getRunnersToDrop() {
        return runnersToDrop;
    }

    public void setRunnersToDrop(List<Runner> runnersToDrop) {
        this.runnersToDrop = runnersToDrop;
    }
}
