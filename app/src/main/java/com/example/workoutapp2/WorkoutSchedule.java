package com.example.workoutapp2;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WorkoutSchedule extends AppCompatActivity {

    private ListView schedules;
    private TextView noSchedules;
    private ArrayList<Schedule> schedulesList;
    private Button addSchedules;
    ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        schedules = findViewById(R.id.scheduleView);
        noSchedules = findViewById(R.id.noSchedules);
        addSchedules = findViewById(R.id.schedulesButton);
        ScheduleHolder.clearSchedule();

        if(schedulesList==null) {
            loadSchedules();
        }

        adapter = new ScheduleAdapter(WorkoutSchedule.this, schedulesList);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode()== Activity.RESULT_OK) {
                            schedulesList.add(ScheduleHolder.getSchedule());
                            for (Schedule schedule1 :
                                    schedulesList) {
                                if (schedule1.getExerciseList().isEmpty()) {
                                    schedulesList.remove(schedule1);
                                }
                            }
                            saveSchedule();
                            adapter.notifyDataSetChanged();
                            schedules.setVisibility(View.VISIBLE);
                            noSchedules.setVisibility(View.GONE);
                        }
                    }
                }
        );

        schedules.setAdapter(adapter);

        schedules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(WorkoutSchedule.this, WorkoutSession.class);
                ScheduleHolder.clearSchedule();
                ScheduleHolder.setSchedule(schedulesList.get(i));
                startActivity(intent);
            }
        });

        addSchedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutSchedule.this, PickExercises.class);
                activityResultLauncher.launch(intent);
            }
        });

        schedules.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(WorkoutSchedule.this, "Removed Schedule", Toast.LENGTH_SHORT).show();
                schedulesList.remove(i);
                adapter.notifyDataSetChanged();
                saveSchedule();
                if(schedulesList.isEmpty()) {
                    schedules.setVisibility(View.GONE);
                    noSchedules.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        if(schedulesList.isEmpty()) {
            schedules.setVisibility(View.GONE);
            noSchedules.setVisibility(View.VISIBLE);
        }

    }

    public void saveSchedule() {
        SharedPreferences preferences = getSharedPreferences("workoutApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(schedulesList);
        editor.putString("schedules",json);
        editor.apply();
    }

    private void loadSchedules() {
        SharedPreferences prefs = getSharedPreferences("workoutApp", MODE_PRIVATE);
        String json = prefs.getString("schedules", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Schedule>>(){}.getType();
            schedulesList = gson.fromJson(json, type); // Convert JSON back to list of schedules
        }
        else {
            schedulesList = new ArrayList<>();
        }
    }

}