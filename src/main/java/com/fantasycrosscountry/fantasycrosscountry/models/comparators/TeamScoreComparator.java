package com.fantasycrosscountry.fantasycrosscountry.models.comparators;

import com.fantasycrosscountry.fantasycrosscountry.models.TeamScore;

import java.util.Comparator;

public class TeamScoreComparator implements Comparator<TeamScore> {
    @Override
    public int compare(TeamScore o1, TeamScore o2){
        return o1.getScore().compareTo(o2.getScore());
    }
}
