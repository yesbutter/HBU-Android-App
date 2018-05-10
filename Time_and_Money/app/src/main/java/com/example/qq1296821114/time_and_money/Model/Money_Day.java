package com.example.qq1296821114.time_and_money.Model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by 12968 on 2018/4/11.
 */

public class Money_Day implements Comparable {

    private double money;
    private Date date;
    public ArrayList<Money> arrayList = new ArrayList<>();

    public Money_Day(double money, Date date) {
        this.date = date;
        this.money = money;
    }

    public Money_Day(Date date) {
        this.date=date;
    }

    public double getMoney() {
        return money;
    }


    public void setMoney(double money) {
        this.money = money;
    }

    public String getDate() {
        return date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
    }

    public Date getDate(int f) {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(@NonNull Object object) {

        if (object == null || !(object instanceof Money_Day)) {
            return -1;
        }
        Money_Day stringItem = (Money_Day) object;

        Calendar calendar = Calendar.getInstance();
        calendar.set(this.date.getYear(), this.date.getMonth(), this.date.getDay(), this.date.getHour(), this.date.getMinuter(), this.date.getSecond());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(stringItem.date.getYear(), stringItem.date.getMonth(), stringItem.date.getDay(), stringItem.date.getHour()
                , stringItem.date.getMinuter(), stringItem.date.getSecond());
        return calendar.compareTo(calendar2);
    }
}
