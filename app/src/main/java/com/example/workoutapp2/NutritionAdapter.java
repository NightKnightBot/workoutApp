package com.example.workoutapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.workoutapp2.dbs.Nutrition;

import java.util.ArrayList;

public class NutritionAdapter extends ArrayAdapter<Nutrition> {
    private Context context;
    private ArrayList<Nutrition> nutritionList;

    class ViewHolder {
        TextView foodName, calories, proteins, carbs, fats;
    }

    public NutritionAdapter(Context context, ArrayList<Nutrition> nutritionList) {
        super(context, R.layout.item_nutrition);
        this.context = context;
        this.nutritionList = nutritionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nutrition, parent, false);
            holder = new ViewHolder();
            holder.foodName = convertView.findViewById(R.id.foodName);
            holder.calories = convertView.findViewById(R.id.calories);
            holder.proteins = convertView.findViewById(R.id.protein);
            holder.carbs = convertView.findViewById(R.id.carbs);
            holder.fats = convertView.findViewById(R.id.fats);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Nutrition nutrition = nutritionList.get(position);
        holder.foodName.setText(nutrition.getFoodName());
        holder.calories.setText(""+nutrition.getCalories());
        holder.proteins.setText(""+nutrition.getProtein());
        holder.carbs.setText(""+nutrition.getCarbs());
        holder.fats.setText(""+nutrition.getFats());

        return convertView;
    }

    @Override
    public int getCount() {
        return nutritionList.size();
    }

    @Nullable
    @Override
    public Nutrition getItem(int position) {
        return nutritionList.get(position);
    }
}