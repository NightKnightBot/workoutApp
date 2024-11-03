package com.example.workoutapp2;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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


        workouts = new ArrayList<>();

        gridView = findViewById(R.id.gridView);
        linearLayout = findViewById(R.id.slidewoho);
        selectedImage = findViewById(R.id.selectedImage);
        selectedName = findViewById(R.id.selectedName);
        selectedDesc = findViewById(R.id.selectedDesc);

        ArrayList<Individual> arr = new ArrayList<>();

        arr.add(new Individual(R.drawable.one, "One", "Desc"));
        arr.add(new Individual(R.drawable.two, "Two", "description"));
        arr.add(new Individual(R.drawable.three, "Three", "Desc"));
        arr.add(new Individual(R.drawable.four, "Four", "description"));

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            List<Exercise> exercises;
            exercises = DatabaseClient.getInstance(MainActivity.this).getExerciseDatabase().exerciseDao().getIdLessThanFour();
            runOnUiThread(() -> {
                for (Exercise exercise :
                        exercises) {
                    workouts.add(new Individual(exercise.getImageResource(), exercise.getName(), exercise.getDescription()));
                }
            });
        }
        );

//      Add exercise
//
//        executor.execute(()->{
//            DatabaseClient.getInstance(MainActivity.this).getExerciseDatabase().exerciseDao().insertExercise(new Exercise());
//        });

        adapter = new IndividualAdapter(this, workouts);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Glide.with(MainActivity.this).load(workouts.get(position).getImage()).into(selectedImage);
                selectedName.setText(workouts.get(position).getName());
                selectedDesc.setText(workouts.get(position).getDescription());

                Toast.makeText(MainActivity.this, "Name "+ workouts.get(position).getName()+"\nDesc "+ workouts.get(position).getDescription(), Toast.LENGTH_SHORT).show();
                slideUp();
            }
        });

        loadExercises();
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
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            List<Exercise> exercises = DatabaseClient.getInstance(MainActivity.this)
                    .getExerciseDatabase().exerciseDao().getAllExercises();

            runOnUiThread(() -> {
                workouts.clear();
                for (Exercise exercise : exercises) {
                    workouts.add(new Individual(exercise.getImageResource(), exercise.getName(), exercise.getDescription()));
                }
                adapter.notifyDataSetChanged();
            });
        });
    }
}