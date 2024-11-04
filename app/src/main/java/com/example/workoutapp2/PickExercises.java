package com.example.workoutapp2;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workoutapp2.dbs.DatabaseClient;
import com.example.workoutapp2.dbs.Exercise;
import com.example.workoutapp2.dbs.ExerciseDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PickExercises extends AppCompatActivity {

    private Button button;
    private Executor executor;
    private GridView gridView;
    private EditText nameOfSchedule;
    ArrayList<Individual> individuals;
    List<Individual> scheduleExercises;
    IndividualAdapter adapter;
    Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pick_exercises);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        executor = Executors.newSingleThreadExecutor();

        individuals = new ArrayList<>();
        adapter = new IndividualAdapter(PickExercises.this, individuals);

        scheduleExercises = new ArrayList<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                ExerciseDao dao = DatabaseClient.getInstance(PickExercises.this).getExerciseDatabase().exerciseDao();
                List<Exercise> exercises = dao.getAllExercises();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Exercise exercise :
                                exercises) {
                            individuals.add(new Individual(exercise.getImageResource(), exercise.getName(), exercise.getDescription(),exercise.getType()));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        button = findViewById(R.id.finish);
        nameOfSchedule = findViewById(R.id.nameOfSchedule);
        gridView = findViewById(R.id.exercisePickerGridView);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int currentColor = Color.TRANSPARENT;

                if (view.getBackground() instanceof ColorDrawable) {
                    currentColor = ((ColorDrawable) view.getBackground()).getColor();
                }

                // Removing from list
                if (currentColor == ContextCompat.getColor(PickExercises.this, R.color.backgroundAlt)) {
                    view.setBackgroundColor(Color.TRANSPARENT);
                    scheduleExercises.remove(individuals.get(i));
                }

                // Adding to List
                else {
                    view.setBackgroundColor(ContextCompat.getColor(PickExercises.this, R.color.backgroundAlt));
                    scheduleExercises.add(individuals.get(i));
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleHolder.setSchedule(new Schedule(nameOfSchedule.getText().toString(), scheduleExercises));
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}