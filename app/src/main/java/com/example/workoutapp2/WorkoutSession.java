package com.example.workoutapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSession extends AppCompatActivity {

    private TextView schedName;
    private EditText numberReps;
    private ListView workList;
    private Button completBtn;

    int repsCompleted = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_session);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Schedule schedule = ScheduleHolder.getSchedule();
        ScheduleHolder.clearSchedule();


        schedName = findViewById(R.id.nameOfSched);
        numberReps = findViewById(R.id.repNumber);
        workList = findViewById(R.id.workoutList);
        completBtn = findViewById(R.id.confirmCompletionem);

        List<Individual> individuals = schedule.getExerciseList();
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(WorkoutSession.this, individuals);
        workList.setAdapter(workoutAdapter);

        completBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repsCompleted++;
                int target = Integer.parseInt(numberReps.getText().toString());
                if(repsCompleted >= target){
                    Intent intent = new Intent(WorkoutSession.this, WorkoutPassed.class);
                    startActivity(intent);
                }
                else{
                    completBtn.setText("Reps Completed: " + repsCompleted);
                }
            }
        });


    }
}