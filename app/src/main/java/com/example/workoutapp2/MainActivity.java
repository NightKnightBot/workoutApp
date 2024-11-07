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
                    dao.insertExercise(new Exercise("Chest Fly", R.drawable.chestflys, "Open and close arms wide", "chest"));
                    dao.insertExercise(new Exercise("Push Ups", R.drawable.chestpushups, "Lower and raise body from floor", "chest"));
                    dao.insertExercise(new Exercise("Bench Press", R.drawable.chestpress, "Press weight up from chest", "chest"));
                    dao.insertExercise(new Exercise("Lunges", R.drawable.legslunges, "Step forward, lowering back knee", "legs"));
                    dao.insertExercise(new Exercise("Leg Press", R.drawable.legspress, "Push weight away with legs", "legs"));
                    dao.insertExercise(new Exercise("Squats", R.drawable.legssquats, "Lower hips and bend knees", "legs"));
                    dao.insertExercise(new Exercise("Hammer Curls", R.drawable.armscurls, "Curl weights with palms facing in", "arms"));
                    dao.insertExercise(new Exercise("Tricep Dips", R.drawable.armstricepdrips, "Lower body on hands, bending elbows", "arms"));
                    dao.insertExercise(new Exercise("Bicep Curls", R.drawable.armscurls, "Curl dumbbells toward shoulders", "arms"));
                    dao.insertExercise(new Exercise("Pull-Ups", R.drawable.backpullups, "Pull body up to bar", "back"));
                    dao.insertExercise(new Exercise("Lat Pulldown", R.drawable.backpulldown, "Pull bar down to chest", "back"));
                    dao.insertExercise(new Exercise("Bent-Over Rows", R.drawable.backrows, "Row weight up with a flat back", "back"));
                    dao.insertExercise(new Exercise("Plank", R.drawable.armscurls, "Hold a straight-arm position", "core"));
                    dao.insertExercise(new Exercise("Russian Twists", R.drawable.coreuhh, "Twist torse side to side", "core"));
                    dao.insertExercise(new Exercise("Bicycle Crunches", R.drawable.corebicyclethingy, "Pedal legs with a twist", "core"));
                    dao.insertExercise(new Exercise("Overhead Shoulder Press", R.drawable.shouldersovpress, "Press weight overhead", "shoulders"));
                    dao.insertExercise(new Exercise("Lateral Raises", R.drawable.shoulderslaterra, "Lift dumbbells to the side", "shoulders"));
                    dao.insertExercise(new Exercise("Face Pulls", R.drawable.backpullups, "Pull cable towards face.", "shoulders"));

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
                    .getExerciseDatabase().exerciseDao().getIdLessThanSix();

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