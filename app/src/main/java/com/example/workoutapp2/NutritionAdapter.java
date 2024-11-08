package com.example.workoutapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp2.dbs.Nutrition;

import java.util.List;

public class NutritionAdapter extends RecyclerView.Adapter<NutritionAdapter.NutritionViewHolder> {
    private List<Nutrition> nutritionList;

    public NutritionAdapter(List<Nutrition> nutritionList) {
        this.nutritionList = nutritionList;
    }

    @NonNull
    @Override
    public NutritionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new NutritionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NutritionViewHolder holder, int position) {
        Nutrition nutrition = nutritionList.get(position);
        holder.foodName.setText(nutrition.getFoodName());
        holder.details.setText("Calories: " + nutrition.getCalories() + ", Protein: " + nutrition.getProtein() + "g, Carbs: " + nutrition.getCarbs() + "g, Fats: " + nutrition.getFats() + "g");
    }

    @Override
    public int getItemCount() {
        return nutritionList.size();
    }

    static class NutritionViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        TextView details;

        NutritionViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(android.R.id.text1);
            details = itemView.findViewById(android.R.id.text2);
        }
    }
}