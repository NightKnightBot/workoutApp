package com.example.workoutapp2.dbs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nutrition")
public class Nutrition {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String foodName;
    private int calories;
    private int protein;
    private int carbs;
    private int fats;
    private long timestamp; // Add this field

    public Nutrition() {
        // Default constructor
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}