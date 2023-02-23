package com.company;

import java.util.Comparator;

public class SortByBurst implements Comparator<Process> {
    @Override
    public int compare(Process o1,Process o2){
        return o1.getBurstTime()-o2.getBurstTime();
    }
}
