package com.example.workoutapp2.dbs;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private static DatabaseClient instance;
    private ExerciseDatabase exerciseDatabase; // Your database class

    private DatabaseClient(Context context) {
        exerciseDatabase = Room.databaseBuilder(context, ExerciseDatabase.class, "nutrition_database")
                .allowMainThreadQueries() // **NOTE:** This is for demo. NEVER allow main thread queries in production!
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public ExerciseDatabase getExerciseDatabase() {
        return exerciseDatabase;
    }
}