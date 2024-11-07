package com.example.workoutapp2;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
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
    String crit;
    private Spinner filterSpin;
    ArrayList<Individual> individuals;
    List<Individual> scheduleExercises;

    ArrayList<Individual> pickedArrayList;
    IndividualAdapter adapter;
    Schedule schedule;
    List<Exercise> exerciseList;

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
        filterSpin = findViewById(R.id.filterSpinner);

        ArrayList<String> filterList = new ArrayList<>();
        filterList.add("All");
        filterList.add("Arms");
        filterList.add("Legs");
        filterList.add("Shoulders");
        filterList.add("Chest");
        filterList.add("Core");
        filterList.add("Back");

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(PickExercises.this, android.R.layout.simple_list_item_1, filterList);
        filterSpin.setAdapter(filterAdapter);
        exerciseList = new ArrayList<>();

        filterSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                crit = filterList.get(position).toLowerCase();
                Toast.makeText(PickExercises.this, "crit: " + crit, Toast.LENGTH_SHORT).show();

                // Clear the exercise list first (before fetching data)
                exerciseList.clear();
                individuals.clear(); // clear the UI list too

                if (crit.equalsIgnoreCase("All")) {
                    // Fetch all exercises
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            exerciseList = DatabaseClient.getInstance(PickExercises.this).getExerciseDatabase().exerciseDao().getAllExercises();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // After data is fetched, clear the individuals list and add new items
                                    individuals.clear();
                                    for (Exercise exercise : exerciseList) {
                                        individuals.add(new Individual(exercise.getImageResource(), exercise.getName(), exercise.getDescription(), exercise.getType()));
                                    }
                                    adapter.notifyDataSetChanged();  // Notify the adapter to refresh the UI
                                }
                            });
                        }
                    });

                } else {
                    // Fetch exercises based on selected filter
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            exerciseList = DatabaseClient.getInstance(PickExercises.this).getExerciseDatabase().exerciseDao().getExerciseOfType(crit);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PickExercises.this, "" + exerciseList.size(), Toast.LENGTH_SHORT).show();
                                    // After data is fetched, clear the individuals list and add new items
                                    individuals.clear();
                                    for (Exercise exercise : exerciseList) {
                                        individuals.add(new Individual(exercise.getImageResource(), exercise.getName(), exercise.getDescription(), exercise.getType()));
                                    }
                                    adapter.notifyDataSetChanged();  // Notify the adapter to refresh the UI
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(PickExercises.this, "No Filters Active", Toast.LENGTH_SHORT).show();
            }
        });

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
                Schedule newSchedule = new Schedule(nameOfSchedule.getText().toString(), scheduleExercises);
                if(newSchedule.getScheduleName().isEmpty() || newSchedule.getScheduleName().isBlank()) {
                    Toast.makeText(PickExercises.this, "Enter Schedule Name", Toast.LENGTH_SHORT).show();
                } else if (newSchedule.getExerciseList().isEmpty()) {
                    Toast.makeText(PickExercises.this, "Please Add Exercises", Toast.LENGTH_SHORT).show();
                }
                else {
                    ScheduleHolder.setSchedule(newSchedule);
                    setResult(Activity.RESULT_OK);
                    finish();
                }

            }
        });
    }
}