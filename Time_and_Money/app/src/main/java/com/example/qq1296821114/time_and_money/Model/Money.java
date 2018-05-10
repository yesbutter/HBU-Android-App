package com.example.qq1296821114.time_and_money.Model;
import android.support.annotation.NonNull;

import com.example.qq1296821114.time_and_money.R;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * 一天金额的花费对象，使得获取数据简单
 * Created by 12968 on 2018/4/4.
 */

public class Money implements Comparable {
    private Date date;
    private double money;
    private String type;
    private int imageId;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Money() {
        date = new Date();
        money = 0;
        type = "支出";
        imageId = R.drawable.ic_xianjin;
    }

    public Money(double in) {
        date = new Date();
        money = in;
        type = "支出";
        imageId = R.drawable.ic_xianjin;
    }

    public Money(double in, Date date) {
        this.date = date;
        money = in;
        type = "支出";
        imageId = R.drawable.ic_xianjin;
    }

    public Money(double in, String type) {
        date = new Date();
        money = in;
        this.type = type;
        imageId = R.drawable.ic_xianjin;
    }

    public Money(double in, String type, int imageId) {
        date = new Date();
        money = in;
        this.type = type;
        this.imageId = imageId;
    }

    public Money(double in, String type, int imageId, Date date) {
        this.date = date;
        money = in;
        this.type = type;
        this.imageId = imageId;
    }

    public double getMoney() {
        return money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    public void setMoney(double money_in) {
        this.money = money_in;
    }

    public void setMoney(String money_in) {
        this.money = Double.parseDouble(money_in);
    }


    public void setDate(String date[]) {
        this.date.setDate(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]), Integer.valueOf(date[3]), Integer.valueOf(date[4]), Integer.valueOf(date[5]));
    }

    public String getDate() {
        return date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
    }
    public String getDateFormat() {
        DecimalFormat decimalFormat=new DecimalFormat("00");
        return date.getYear() + "-" + decimalFormat.format(date.getMonth()) + "-" + decimalFormat.format(date.getDay());
    }
    public Date getDate(int flag) {
        return date;
    }

    public String toString() {
        return type + "-" + date.toString();
    }

    @Override
    public int compareTo(@NonNull Object object) {
        if (object == null || !(object instanceof Money)) {
            return -1;
        }
        Money stringItem = (Money) object;

        Calendar calendar = Calendar.getInstance();
        calendar.set(this.date.getYear(), this.date.getMonth(), this.date.getDay(), this.date.getHour(), this.date.getMinuter(), this.date.getSecond());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(stringItem.date.getYear(), stringItem.date.getMonth(), stringItem.date.getDay(), stringItem.date.getHour()
                , stringItem.date.getMinuter(), stringItem.date.getSecond());
        return -calendar.compareTo(calendar2);
    }
}
