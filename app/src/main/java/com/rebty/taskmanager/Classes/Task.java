package com.rebty.taskmanager.Classes;

import java.util.Date;

public class Task {
    public String header;
    String text;
    String timeString;
    int iconColor;
    public long longTime;
    Date time;

    public Task(String header, String text, int iconColor, int year, int month, int day, int hour, int minute) {
        this.header = header;
        this.text = text;
        this.iconColor = iconColor;
        time = new Date(year, month, day, hour, minute);
        timeString = time.toLocaleString();
        longTime = time.getTime();
    }

    public Task(String header, String text, int iconColor, long intTime) {
        this.header = header;
        this.text = text;
        timeString = (new Date(intTime)).toLocaleString();
        this.iconColor = iconColor;
        this.longTime = intTime;
    }
}
