package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

public class TradeProposal {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Team proposer;

    @ManyToOne
    private Team proposee;

    private List<Integer> proposerRunners;

    private List<Integer> proposeeRunners;

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

    public List<Integer> getProposerRunners() {
        return proposerRunners;
    }

    public void setProposerRunners(List<Integer> proposerRunners) {
        this.proposerRunners = proposerRunners;
    }

    public List<Integer> getProposeeRunners() {
        return proposeeRunners;
    }

    public void setProposeeRunners(List<Integer> proposeeRunners) {
        this.proposeeRunners = proposeeRunners;
    }
}
