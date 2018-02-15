package com.chnumarks.models;

import java.util.ArrayList;

/**
 * Created by User on 15.02.2018.
 */

public class StudentInfo {
    public String name;
    public ArrayList<Double> mark;
    public ArrayList<Long> labId;

    public StudentInfo(String name, ArrayList<Double> mark, ArrayList<Long> labId) {
        this.name = name;
        this.mark = mark;
        this.labId = labId;
    }

    public StudentInfo(String name, long[] labId, double[] mark) {
        this.name = name;
        this.mark = new ArrayList<>();
        this.labId = new ArrayList<>();
        for (int i = 0; i < labId.length ; i++) {
            this.mark.add(mark[i]);
            this.labId.add(labId[i]);
        }
    }

    public double getTotalSum(){
        int sum = 0;
        for (double temp:
             mark) {
            sum += temp;
        }
        return sum;
    }

    public StudentInfo(String name, long labId, double mark) {
        this.name = name;
        this.mark = new ArrayList<>();
        this.labId = new ArrayList<>();
        this.labId.add(labId);
        this.mark.add(mark);

    }
}
