package com.fantasycrosscountry.fantasycrosscountry.models.comparators;


import com.fantasycrosscountry.fantasycrosscountry.models.Performance;

import java.util.Comparator;

public class PerformanceComparator implements Comparator<Performance> {
    @Override
    public int compare(Performance o1, Performance o2){
        return o1.getTime().compareTo(o2.getTime());
    }

}
