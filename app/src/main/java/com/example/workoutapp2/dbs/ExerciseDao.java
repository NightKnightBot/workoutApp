package com.example.workoutapp2.dbs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.workoutapp2.Individual;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert
    void insertExercise(Exercise exercise);

    @Query("SELECT * FROM exercise")
    List<Exercise> getAllExercises();
}
