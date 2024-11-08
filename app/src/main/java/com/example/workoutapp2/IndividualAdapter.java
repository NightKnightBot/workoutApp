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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IndividualAdapter extends ArrayAdapter<Individual> {

    class ViewHolder {
        ImageView image;
        TextView name;
    }

    Context context;
    ArrayList<Individual> individuals;
    Set<Integer> selectedItems = new HashSet<>();
    public IndividualAdapter(@NonNull Context context, ArrayList<Individual> individuals) {
        super(context, R.layout.individual);
        this.context = context;
        this.individuals = individuals;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.individual, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.image);
            holder.name = convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (selectedItems.contains(position)) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.backgroundAlt)); // Highlight color
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT); // Default color
        }

        Individual individual = individuals.get(position);
        Glide.with(context).load(individual.getImage()).into(holder.image);
        holder.name.setText(individual.getName());
        return convertView;
    }

    public void toggleSelection(int position) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position);
        } else {
            selectedItems.add(position);
        }
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selectedItems.clear();
    }

    @Override
    public int getCount() {
        return individuals.size();
    }

    @Nullable
    @Override
    public Individual getItem(int position) {
        return individuals.get(position);
    }
}
