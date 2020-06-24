package com.choith.gamedailyattendance;

import android.graphics.drawable.Drawable;

public class NewAppList {

    private Drawable icon;
    private String name;
    private String pkg;

    public NewAppList(Drawable icon, String name, String pkg){
        this.icon=icon;
        this.name=name;
        this.pkg=pkg;
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

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
}
