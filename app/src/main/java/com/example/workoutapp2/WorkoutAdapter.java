package com.example.workoutapp2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends ArrayAdapter<Individual> {
    class ViewHolder {
        TextView textViewTitle;
        ImageView imageView;
    }

    Context context;
    List<Individual> schedules;
    private List<Boolean> selectedItems;

    public WorkoutAdapter(Context context, List<Individual> schedules) {
        super(context, R.layout.workout_adapter);
        this.context = context;
        this.schedules = schedules;
        this.selectedItems = new ArrayList<>();
        for (int i = 0; i < schedules.size(); i++) {
            selectedItems.add(false);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.workout_adapter, parent, false);
            holder = new ViewHolder();
            holder.textViewTitle = convertView.findViewById(R.id.wktName);
            holder.imageView = convertView.findViewById(R.id.wktImage);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Individual schedule = schedules.get(position);
        holder.textViewTitle.setText(schedule.getName());
        Glide.with(context).load(schedule.getImage()).into(holder.imageView);

        if (selectedItems.get(position)) {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.scheduleAdapter));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Nullable
    @Override
    public Individual getItem(int position) {
        return schedules.get(position);
    }

    public void toggleSelection(int position) {
        selectedItems.set(position, !selectedItems.get(position));
        notifyDataSetChanged();
    }

    public void clearSelections() {
        for (int i = 0; i < selectedItems.size(); i++) {
            selectedItems.set(i, false);
        }
        notifyDataSetChanged();
    }
}
