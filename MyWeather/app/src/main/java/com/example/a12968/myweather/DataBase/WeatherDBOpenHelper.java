package com.example.a12968.myweather.DataBase;

/**
 * Created by t-lidashao on 18-2-18.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 12968 on 2018/2/9.
 */

public class WeatherDBOpenHelper extends SQLiteOpenHelper {

    /**
     * Province建表语句
     */
    public static final String CREATE_PROVINCE="create table Province("
            +"id integer primary key autoincrement,"
            +"province_name text,"
            +"province_code text)";

    /**
     * City建表语句
     */
    public static final String CREATE_CITY="create table City("
            +"id integer primary key autoincrement,"
            +"city_name text,"
            +"city_code text,"
            +"province_id integer)";

    /**
     * County建表语句
     */
    public static final String CREATE_COUNTY="create table County("
            +"id integer primary key autoincrement,"
            +"county_name text,"
            +"county_code text,"
            +"city_id integer)";


    public WeatherDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PROVINCE);
        sqLiteDatabase.execSQL(CREATE_CITY);
        sqLiteDatabase.execSQL(CREATE_COUNTY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

