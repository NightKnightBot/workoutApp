package com.example.workoutapp2;

import com.example.workoutapp2.dbs.Exercise;

import java.util.List;

public class Schedule {
    private String scheduleName;
    private List<Individual> exerciseList;

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public List<Individual> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Individual> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public Schedule(String scheduleName, List<Individual> exerciseList) {
        this.scheduleName = scheduleName;
        this.exerciseList = exerciseList;
    }
}
