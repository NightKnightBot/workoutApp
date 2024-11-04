package com.example.workoutapp2;

public class ScheduleHolder {
    private static Schedule schedule;

    private ScheduleHolder(){}  // Prevents instantiation

    public static void setSchedule(Schedule schedule) {
        ScheduleHolder.schedule = schedule;
    }

    public static Schedule getSchedule() {
        return ScheduleHolder.schedule;
    }

    public static void clearSchedule() {
        ScheduleHolder.schedule = null;
    }
}
