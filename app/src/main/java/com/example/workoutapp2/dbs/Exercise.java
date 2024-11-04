package com.example.workoutapp2.dbs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int imageResource;
    private String description;
    private String type;

    public Exercise(String name, int imageResource, String description, String type) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.type = type;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
