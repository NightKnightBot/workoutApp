package com.example.workoutapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.workoutapp2.dbs.DatabaseClient;

import com.example.workoutapp2.data.AppDatabase;
import com.example.workoutapp2.data.Nutrition;
import com.example.workoutapp2.data.NutritionDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NutritionTrackerActivity extends AppCompatActivity {

    private EditText food;
    private EditText cal;
    private EditText protein;
    private EditText carbs;
    private EditText fats;

    private Executor executor = Executors.newSingleThreadExecutor();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_tracker);

        food = findViewById(R.id.slidewoho);
        cal = findViewById(R.id.editTextCal);
        protein = findViewById(R.id.editTextPro);
        carbs = findViewById(R.id.editTextCarb);
        fats = findViewById(R.id.editTextFat);
    }

    private void saveNutritionData() {
        String foodName = food.getText().toString().trim();
        String calStr = cal.getText().toString().trim();
        String proStr = protein.getText().toString().trim();
        String carbStr = carbs.getText().toString().trim();
        String fatStr = fats.getText().toString().trim();

        if (foodName.isEmpty() || calStr.isEmpty() || proStr.isEmpty() || carbStr.isEmpty() || fatStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Nutrition nutrition = new Nutrition();
        nutrition.setFoodName(foodName);
        nutrition.setCalories(Integer.parseInt(calStr));
        nutrition.setProtein(Integer.parseInt(proStr));
        nutrition.setCarbs(Integer. parseInt(carbStr));
        nutrition.setFats(Integer.parseInt(fatStr));

        executor.execute(new Runnable() {
            @Override
            public void run() {
                DatabaseClient.getInstance(getApplicationContext()).getNutritionDao().insert(nutrition);
                runOnUiThread(() -> Toast.makeText(NutritionTrackerActivity.this, "Nutrition data saved", Toast.LENGTH_SHORT).show());
            }
        });
    }
}