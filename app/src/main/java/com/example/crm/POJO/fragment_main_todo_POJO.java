package com.example.crm.POJO;

import android.widget.TextView;

public class fragment_main_todo_POJO {
    String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    public fragment_main_todo_POJO(String day, String date) {
        this.day = day;
        this.date = date;
    }
}
