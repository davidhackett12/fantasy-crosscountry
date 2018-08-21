package com.fantasycrosscountry.fantasycrosscountry.models.comparators;

import com.fantasycrosscountry.fantasycrosscountry.models.OverallScore;

import java.util.Comparator;

public class OverallScoreComparator implements Comparator<OverallScore> {
    @Override
    public int compare(OverallScore o1, OverallScore o2) {
        return o1.getScore().compareTo(o2.getScore());
    }
}
