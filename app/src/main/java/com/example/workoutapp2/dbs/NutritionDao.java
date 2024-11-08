package com.example.workoutapp2.dbs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NutritionDao {
    @Insert
    void insertNutrition(Nutrition nutrition);

    @Query("SELECT * FROM nutrition ORDER BY timestamp DESC") // Order by timestamp
    List<Nutrition> getAllNutrition();

    void insert(Nutrition nutrition);
}