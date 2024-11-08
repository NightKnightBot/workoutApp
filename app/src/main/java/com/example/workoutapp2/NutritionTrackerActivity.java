package com.example.workoutapp2;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workoutapp2.dbs.Nutrition;

import java.util.List;

public class NutritionTrackerActivity extends AppCompatActivity {
    private ListView nutritionListView;
    private NutritionAdapter nutritionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_tracker);

        nutritionListView = findViewById(R.id.nutritionListView);

        // Assuming you have a method to get the nutrition data
        List<Nutrition> nutritionList = getNutritionData();

        // Set up the adapter
        nutritionAdapter = new NutritionAdapter(this, nutritionList);
        nutritionListView.setAdapter(nutritionAdapter);
    }

    private List<Nutrition> getNutritionData() {
        // This method should return a list of Nutrition objects
        // For example, you can create a sample list here
        return List.of(
                new Nutrition("Apple", 95, 0.5, 25, 0.3),
                new Nutrition("Banana", 105, 1.3, 27, 0.3),
                new Nutrition("Chicken Breast", 165, 31, 0, 3.6)
        );
    }
}