package com.example.qq1296821114.time_and_money.Model;

import android.util.Log;

import com.example.qq1296821114.time_and_money.Util.MyUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

/**
 * Created by 12968 on 2018/4/8.
 */

public class Date {
    private int year, month, day, hour, minuter, second, week;
    private final static int weeks[] = new int[]{7, 1, 2, 3, 4, 5, 6};

    public Date() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DATE);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minuter = c.get(Calendar.MINUTE);
        second = c.get(Calendar.SECOND);
        week = weeks[c.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        hour = 12;
        minuter = 0;
        second = 0;
        week = weeks[c.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public Date(int year, int month, int day, int hour, int minuter, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minuter = minuter;
        this.second = second;
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        week = weeks[c.get(Calendar.DAY_OF_WEEK) - 1];
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinuter() {
        return minuter;
    }

    public void setMinuter(int minuter) {
        this.minuter = minuter;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setDate(int year,int month,int day)
    {
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public void setDate(int year,int month,int day,int hour,int minuter,int second)
    {
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        this.minuter=minuter;
        this.second=second;
    }
    public String toString()
    {
        return year+"-"+month+"-"+day+"-"+hour+"-"+minuter+"-"+second;
    }
}
