package com.example.workoutapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NutritionAdapter extends ArrayAdapter<Nutrition> {
    private final Context context;
    private final List<Nutrition> nutritionList;

    public NutritionAdapter(Context context, List<Nutrition> nutritionList) {
        super(context, R.layout.item_nutrition, nutritionList);
        this.context = context;
        this.nutritionList = nutritionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout for each item if convertView is null
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_nutrition, parent, false);
        }

        // Get the current Nutrition item using getItem()
        Nutrition nutrition = getItem(position); // Using the inherited getItem method

        // Set the data to the views
        TextView foodName = convertView.findViewById(R.id.foodName);
        TextView calories = convertView.findViewById(R.id.calories);
        TextView protein = convertView.findViewById(R.id.protein);
        TextView carbs = convertView.findViewById(R.id.carbs);
        TextView fats = convertView.findViewById(R.id.fats);

        // Check if nutrition is not null before setting data
        if (nutrition != null) {
            foodName.setText(nutrition.getFoodName());
            calories.setText(String.valueOf(nutrition.getCalories()));
            protein.setText(String.valueOf(nutrition.getProtein()));
            carbs.setText(String.valueOf(nutrition.getCarbs()));
            fats.setText(String.valueOf(nutrition.getFats()));
        }

        return convertView;
    }
}