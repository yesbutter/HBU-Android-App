package com.example.qq1296821114.time_and_money.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 帮助数据库建表
 * Created by 12968 on 2018/4/6.
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {


    //数据库建表语句，类型应该还有其他的。
    public static final String CREATE_MONEY_DAY = "create table Money("
            + "id integer primary key autoincrement,"
            + "date text,"
            + "money real,"
            + "image_id text,"
            + "type text)";

    public static final String CREATE_PLAN = "create table Plan("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "motto text,"
            + "body text,"
            + "date text)";


    public static final String CREATE_TIME = "create table Time("
            + "id integer primary key autoincrement,"
            + "body text,"
            + "date text,"
            + "main text)";


    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //生成表格
        sqLiteDatabase.execSQL(CREATE_MONEY_DAY);
        sqLiteDatabase.execSQL(CREATE_PLAN);
        sqLiteDatabase.execSQL(CREATE_TIME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
