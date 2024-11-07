package com.example.workoutapp2.dbs;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient instance;

    private ExerciseDatabase exerciseDatabase; // or your specific database name

    private DatabaseClient(Context context) {
        this.context = context;
        exerciseDatabase = Room.databaseBuilder(context, ExerciseDatabase.class, "your_database_name")
                .allowMainThreadQueries() //  **NOTE:**  This is for demo. NEVER allow main thread queries in production!
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