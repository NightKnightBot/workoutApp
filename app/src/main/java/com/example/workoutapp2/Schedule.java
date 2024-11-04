package com.example.workoutapp2;

import com.example.workoutapp2.dbs.Exercise;

import java.util.List;

public class Schedule {
    private String scheduleName;
    private List<Exercise> exerciseList;

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public Schedule(String scheduleName, List<Exercise> exerciseList) {
        this.scheduleName = scheduleName;
        this.exerciseList = exerciseList;
    }
}
