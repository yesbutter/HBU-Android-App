package com.example.qq1296821114.time_and_money.Util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 我的工具类
 * Created by 12968 on 2018/4/1.
 */

public class MyUtil {

    //显示一个日历对话框
    public static void showDatePickerDialog(final Activity activity, final TextView tv) {

        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date date = new Date();
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                calendar1.setTime(date);
                calendar2.set(year, monthOfYear, dayOfMonth);
                if (calendar1.compareTo(calendar2) >= 0) {
                    monthOfYear++;
                    tv.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                } else {
                    Toast.makeText(activity, "不能记录未来的账", Toast.LENGTH_SHORT).show();
                }

                if (calendar1.compareTo(calendar2) == 0) {
                    tv.setText("今日");
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void showDatePickerDialog(final Activity activity, final TextView tv, final int flag) {

        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date date = new Date();
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                calendar1.setTime(date);
                calendar2.set(year, monthOfYear, dayOfMonth);
                monthOfYear++;
                tv.setText(year + "-" + monthOfYear + "-" + dayOfMonth);

                if (calendar1.compareTo(calendar2) == 0) {
                    tv.setText("今日");
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    //日期格式转化
    public static String getDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    //日期格式转化
    public static String getDate(Date date, SimpleDateFormat format) {
        return format.format(date);
    }


    public static boolean isNetworkAvailable(){
        return true;
    }

}
