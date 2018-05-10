package com.example.qq1296821114.time_and_money.Model;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * 时间显示对象
 * Created by 12968 on 2018/4/4.
 */

public class Time implements Comparable {

    private String text1, text2;
    private Date date;

    public Time() {
        text1 = "无参构造";
        text2 = "这个人很懒，什么都没填写";
        date = new Date();
    }

    public Time(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    public Time(String text1, String text2, Date date) {

        this.text1 = text1;
        this.text2 = text2;
        this.date = date;
    }

    public String getDate() {
        return date.getYear() + "-" + date.getMonth() + "-" + date.getDay();

    }

    public void setDate(String date[]) {
        this.date.setDate(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]));
    }


    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    @Override
    public int compareTo(@NonNull Object object) {
        if (object == null || !(object instanceof Time)) {
            return -1;
        }
        Time stringItem = (Time) object;

        Calendar calendar = Calendar.getInstance();
        calendar.set(this.date.getYear(), this.date.getMonth(), this.date.getDay(), this.date.getHour(), this.date.getMinuter(), this.date.getSecond());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(stringItem.date.getYear(), stringItem.date.getMonth(), stringItem.date.getDay(), stringItem.date.getHour()
                , stringItem.date.getMinuter(), stringItem.date.getSecond());
        return -calendar.compareTo(calendar2);
    }
}
