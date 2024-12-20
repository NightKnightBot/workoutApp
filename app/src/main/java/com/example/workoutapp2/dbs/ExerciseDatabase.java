package com.example.workoutapp2.dbs;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Exercise.class, Nutrition.class}, version = 1)
public abstract class ExerciseDatabase extends RoomDatabase {
    public abstract ExerciseDao exerciseDao();
    public abstract NutritionDao getNutritionDao();
}