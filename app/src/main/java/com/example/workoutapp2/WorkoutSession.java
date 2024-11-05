package com.example.workoutapp2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.ArrayList;
import java.util.List;

public class WorkoutSession extends AppCompatActivity {

    private TextView schedName, completBtn;
    private EditText numberReps;
    private ListView workList;
    WorkoutAdapter workoutAdapter;
    int sizeOfList;
    ArrayList<Individual> clicked;
    List<Individual> individuals;

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
        individuals = schedule.getExerciseList();
        sizeOfList = individuals.size();
        clicked = new ArrayList<>();

        schedName = findViewById(R.id.nameOfSched);
        numberReps = findViewById(R.id.repNumber);
        workList = findViewById(R.id.workoutList);
        completBtn = findViewById(R.id.confirmCompletionem);

        schedName.setText(schedule.getScheduleName().toString());

        workoutAdapter = new WorkoutAdapter(WorkoutSession.this, individuals);
        workList.setAdapter(workoutAdapter);

        workList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                numberReps.clearFocus();
                hideKeyboard(view);

                if( !clicked.contains(individuals.get(i)) ) {
                    clicked.add(individuals.get(i));
                    view.setBackgroundColor(ContextCompat.getColor(WorkoutSession.this, R.color.scheduleAdapter));
                    sizeOfList--;
                    if(sizeOfList <= 0) {
                        Toast.makeText(WorkoutSession.this, "toasty", Toast.LENGTH_SHORT).show();
                        int count = workList.getChildCount();
                        for (int iter = 0; iter<count; iter++) {
                            View viewChild = workList.getChildAt(iter);
                            viewChild.setBackgroundColor(Color.TRANSPARENT);
                            sizeOfList = individuals.size();
                            clicked.clear();
                        }

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
                }
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}