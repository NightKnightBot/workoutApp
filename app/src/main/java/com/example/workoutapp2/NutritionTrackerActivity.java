package com.example.workoutapp2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workoutapp2.dbs.DatabaseClient;
import com.example.workoutapp2.dbs.Nutrition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NutritionTrackerActivity extends AppCompatActivity {
    private ListView nutritionListView;
    private NutritionAdapter nutritionAdapter;
    private EditText foodName, calories, fats, proteins, carbs;
    private Button button;
    private ArrayList<Nutrition> getNutrition;
    Executor executor = Executors.newSingleThreadExecutor();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_tracker);

        nutritionListView = findViewById(R.id.nutritionListView);
        button = findViewById(R.id.saveNutrition);
        foodName = findViewById(R.id.foodName);
        calories = findViewById(R.id.calories);
        fats = findViewById(R.id.fats);
        proteins = findViewById(R.id.protein);
        carbs = findViewById(R.id.carbs);

        getNutrition = new ArrayList<>();

        // Set up the adapter
        nutritionAdapter = new NutritionAdapter(NutritionTrackerActivity.this, getNutrition);
        nutritionListView.setAdapter(nutritionAdapter);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                getNutrition.addAll(DatabaseClient.getInstance(NutritionTrackerActivity.this).getExerciseDatabase().getNutritionDao().getAllNutrition());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nutritionAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(foodName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(NutritionTrackerActivity.this, "Please enter name of food", Toast.LENGTH_SHORT).show();
                } else if (calories.getText().toString().trim().isEmpty()) {
                    Toast.makeText(NutritionTrackerActivity.this, "Please enter number of calories", Toast.LENGTH_SHORT).show();
                } else if (fats.getText().toString().trim().isEmpty()) {
                    Toast.makeText(NutritionTrackerActivity.this, "Please enter number of fats", Toast.LENGTH_SHORT).show();
                } else if (proteins.getText().toString().trim().isEmpty()) {
                    Toast.makeText(NutritionTrackerActivity.this, "Please enter number of proteins", Toast.LENGTH_SHORT).show();
                } else if (carbs.getText().toString().trim().isEmpty()) {
                    Toast.makeText(NutritionTrackerActivity.this, "Please enter number of carbs", Toast.LENGTH_SHORT).show();
                } else {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseClient.getInstance(NutritionTrackerActivity.this).getExerciseDatabase().getNutritionDao().insert(new Nutrition(
                                    foodName.getText().toString().trim(),
                                    Double.parseDouble(calories.getText().toString().trim()),
                                    Double.parseDouble(proteins.getText().toString().trim()),
                                    Double.parseDouble(carbs.getText().toString().trim()),
                                    Double.parseDouble(fats.getText().toString().trim())
                            ));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    nutritionAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}