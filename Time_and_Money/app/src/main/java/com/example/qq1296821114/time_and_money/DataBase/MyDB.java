package com.example.qq1296821114.time_and_money.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.qq1296821114.time_and_money.Model.Date;
import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.Model.Plan;
import com.example.qq1296821114.time_and_money.Model.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * 用来存放数据。
 * Created by 12968 on 2018/4/6.
 */

public class MyDB {
    /**
     * 数据库名称
     */
    public static final String DB_NAME = "Yesbutter";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    //保证全局只有一个数据库
    private static MyDB myDB;


    private SQLiteDatabase sqLiteDatabase;

    //构造方法私有化
    private MyDB(Context context) {
        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, DB_NAME, null, VERSION);
        sqLiteDatabase = myDBOpenHelper.getWritableDatabase();
    }

    //进程加锁，使得全局就一个数据库
    public synchronized static MyDB getMyDB(Context context) {
        if (myDB == null) {
            myDB = new MyDB(context);

            if (myDB.loadMoney_Day().isEmpty()) {
            /*
                for (int i = 0; i < 10; i++) {
                    Random rnd = new Random();
                    double rndint = rnd.nextInt(100);
                    for (int year = 2016; year < 2018; year++) {
                        for (int k = 0; k < 12; k++) {
                            for (int j = 0; j < 30; j++) {
                                myDB.saveMoney(new Money(rndint, new Date(year, k, j)));
                            }
                        }
                    }
                }
                */
                myDB.saveMoney(new Money(20));
                myDB.saveMoney(new Money(12));
            }
            if (myDB.loadPlan().isEmpty()) {
                myDB.savePlan(new Plan("TestPlan", "Motto", "_Test_Body", new Date()));
            }
            if (myDB.loadTime().isEmpty()) {
                myDB.saveTime(new Time("Demo", "Body", new Date()));
                myDB.saveTime(new Time("Demo2", "Body2", new Date()));
            }
        }
        return myDB;
    }


    //存储数据
    public void saveMoney(Money money) {
        if (money != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("date", money.getDate(1).toString());
            contentValues.put("money", money.getMoney());
            contentValues.put("image_id", String.valueOf(money.getImageId()));
            contentValues.put("type", money.getType());
            sqLiteDatabase.insert("Money", null, contentValues);
        }
    }

    public void savePlan(Plan plan) {
        if (plan != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", plan.getPlanname());
            contentValues.put("motto", plan.getPlanMotto());
            contentValues.put("body", plan.getPlan_body());
            contentValues.put("date", plan.getDate());
            sqLiteDatabase.insert("Plan", null, contentValues);
        }
    }


    public void saveTime(Time time) {
        if (time != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("body", time.getText1());
            contentValues.put("main", time.getText2());
            contentValues.put("date", time.getDate());
            sqLiteDatabase.insert("Time", null, contentValues);
        }
    }

    //加载数据，还有内容没有完成
    public List<Money> loadMoney_Day() {
        List<Money> list = new ArrayList<Money>();
        Cursor cursor = sqLiteDatabase.query("Money", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Money money = new Money();
                money.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
                money.setType(cursor.getString(cursor.getColumnIndex("type")));
                money.setImageId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("image_id"))));
                money.setDate(cursor.getString(cursor.getColumnIndex("date")).split("-"));
                money.setId(cursor.getInt(cursor.getColumnIndex("id")));
                list.add(money);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public List<Plan> loadPlan() {
        List<Plan> list = new ArrayList<Plan>();
        Cursor cursor = sqLiteDatabase.query("Plan", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Plan plan = new Plan();
                plan.setPlanname(cursor.getString(cursor.getColumnIndex("name")));
                plan.setPlanMotto(cursor.getString(cursor.getColumnIndex("motto")));
                plan.setPlan_body(cursor.getString(cursor.getColumnIndex("body")));
                list.add(plan);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }


    public List<Time> loadTime() {
        List<Time> list = new ArrayList<Time>();
        Cursor cursor = sqLiteDatabase.query("Time", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Time time = new Time();
                time.setText1(cursor.getString(cursor.getColumnIndex("body")));
                time.setText2(cursor.getString(cursor.getColumnIndex("main")));
                time.setDate(cursor.getString(cursor.getColumnIndex("date")).split("-"));
                list.add(time);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public void delMoney(Money money) {
        sqLiteDatabase.delete("Money", "id=?", new String[]{String.valueOf(money.getId())});
    }

    public void upDateMoney(Money oldMoney, Money newMoney) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("money", newMoney.getMoney());
        contentValues.put("image_id", newMoney.getImageId());
        contentValues.put("date", newMoney.getDate(1).toString());
        contentValues.put("type", newMoney.getType());

        sqLiteDatabase.update("Money", contentValues, "id=?", new String[]{String.valueOf(oldMoney.getId())});
    }
}