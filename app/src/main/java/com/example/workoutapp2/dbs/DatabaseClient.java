package com.example.workoutapp2.dbs;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient instance;

    private ExerciseDatabase exerciseDatabase;
    private DatabaseClient(Context context) {
        this.context = context;
        exerciseDatabase = Room.databaseBuilder(context, ExerciseDatabase.class, "WorkoutDB")
//                .fallbackToDestructiveMigration()
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public ExerciseDatabase getExerciseDatabase() {
        return exerciseDatabase;
    }
}
