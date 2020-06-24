package com.choith.gamedailyattendance;

import android.graphics.drawable.Drawable;

class MyAppList {
    private Drawable icon;
    private String name;
    private int hour;
    private int minute;
    private long last;

    public MyAppList(Drawable icon, String name, int hour, int minute, long last){
        this.icon=icon;
        this.name=name;
        this.hour=hour;
        this.minute=minute;
        this.last=last;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }
}
