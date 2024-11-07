package com.example.workoutapp2.dbs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NutritionDao {
    @Insert
    void insertNutrition(Nutrition nutrition);

    @Query("SELECT * FROM nutrition")
    List<Nutrition> getAllNutrition();
}