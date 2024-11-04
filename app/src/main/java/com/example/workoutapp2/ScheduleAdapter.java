package com.example.workoutapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {
    class ViewHolder {
        TextView textViewTitle;
    }

    Context context;
    ArrayList<Schedule> schedules;

    public ScheduleAdapter(Context context, ArrayList<Schedule> schedules) {
        super(context, R.layout.schedule_adapter);
        this.context = context;
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.schedule_adapter, parent, false);
            holder = new ViewHolder();
            holder.textViewTitle = convertView.findViewById(R.id.scheduleAdapterTextView1);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Schedule schedule = schedules.get(position);
        holder.textViewTitle.setText(schedule.getScheduleName());

        return convertView;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Nullable
    @Override
    public Schedule getItem(int position) {
        return schedules.get(position);
    }
}
