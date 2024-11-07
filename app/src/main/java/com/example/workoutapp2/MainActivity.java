package com.example.workoutapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.workoutapp2.dbs.DatabaseClient;
import com.example.workoutapp2.dbs.Exercise;
import com.example.workoutapp2.dbs.ExerciseDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private LinearLayout linearLayout;

    private ImageView selectedImage;
    private TextView selectedName;
    private TextView selectedDesc;
    ArrayList<Individual> workouts;

    private Button selectWorkout;

    Executor executor;

    IndividualAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        executor = Executors.newSingleThreadExecutor();

        workouts = new ArrayList<>();

        gridView = findViewById(R.id.gridView);
        linearLayout = findViewById(R.id.slidewoho);
        selectedImage = findViewById(R.id.selectedImage);
        selectedName = findViewById(R.id.selectedName);
        selectedDesc = findViewById(R.id.selectedDesc);
        selectWorkout = findViewById(R.id.selectWorkout);

//        Add exercise

        executor.execute(new Runnable() {
            @Override
            public void run() {
                ExerciseDao dao = DatabaseClient.getInstance(MainActivity.this).getExerciseDatabase().exerciseDao();
                boolean databaseIsEmpty = dao.getRowCount() == 0;
                if(databaseIsEmpty) {
                    dao.insertExercise(new Exercise("Exercise 1", R.drawable.one, "Descripton 1", "chest"));
                    dao.insertExercise(new Exercise("Exercise 2", R.drawable.two, "Descripton 2", "legs"));
                    dao.insertExercise(new Exercise("Exercise 3", R.drawable.three, "Descripton 3", "arms"));
                    dao.insertExercise(new Exercise("Exercise 4", R.drawable.four, "Descripton 4", "back"));
                    
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Database has been populated", Toast.LENGTH_SHORT).show();
                            loadExercises();
                        }
                    });
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Database is full", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        adapter = new IndividualAdapter(this, workouts);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Glide.with(MainActivity.this).load(workouts.get(position).getImage()).into(selectedImage);
                selectedName.setText(workouts.get(position).getName());
                selectedDesc.setText(workouts.get(position).getDescription());
                slideUp();
            }
        });

        loadExercises();

        selectWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WorkoutSchedule.class);
                startActivity(intent);
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideDown();
            }
        });
    }

    void slideUp() {
        if(linearLayout.getVisibility()==View.GONE) {
            linearLayout.setVisibility(View.VISIBLE);
            Animation slideup = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up);
            linearLayout.startAnimation(slideup);
        }
    }

    void slideDown() {
        Animation slidedown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_down);
        linearLayout.startAnimation(slidedown);
        linearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (linearLayout.getVisibility()==View.GONE) {
            super.onBackPressed();
        }
        else {
            slideDown();
        }
    }

    private void loadExercises() {
        executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            List<Exercise> exercises = DatabaseClient.getInstance(MainActivity.this)
                    .getExerciseDatabase().exerciseDao().getAllExercises();

            runOnUiThread(() -> {
                workouts.clear();
                for (Exercise exercise : exercises) {
                    workouts.add(new Individual(exercise.getImageResource(), exercise.getName(), exercise.getDescription(), exercise.getType()));
                }
                adapter.notifyDataSetChanged();
            });
        });
    }
}