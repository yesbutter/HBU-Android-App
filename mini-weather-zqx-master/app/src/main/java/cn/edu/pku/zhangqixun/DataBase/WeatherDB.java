package cn.edu.pku.zhangqixun.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.zhangqixun.bean.City;
import cn.edu.pku.zhangqixun.bean.County;
import cn.edu.pku.zhangqixun.bean.Province;

/**
 * Created by 12968 on 2018/2/9.
 */

public class WeatherDB {
    /**
     * 数据库名称
     */
    public static final String DB_NAME = "Yesbutter_weather";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static WeatherDB weatherDB;

    private SQLiteDatabase sqLiteDatabase;

    /**
     * 将构造方法私有化
     */
    private WeatherDB(Context context) {
        WeatherDBOpenHelper weatherDBOpenHelper = new WeatherDBOpenHelper(context, DB_NAME, null, VERSION);
        sqLiteDatabase = weatherDBOpenHelper.getWritableDatabase();
    }

    public synchronized static WeatherDB getWeatherDB(Context context) {
        if (weatherDB == null) {
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("province_name", province.getProvinceName());
            contentValues.put("province_code", province.getProvinceCode());
            sqLiteDatabase.insert("Province", null, contentValues);
        }
    }

    public List<Province> loadProvince() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = sqLiteDatabase.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public void saveCity(City city) {
        if (city != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_name", city.getCityName());
            contentValues.put("city_code", city.getCityCode());
            contentValues.put("province_id", city.getProvinceId());
            sqLiteDatabase.insert("City", null, contentValues);
        }
    }

    public List<City> loadCity(int provinceId) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = sqLiteDatabase.query("City", null, "Province_id = ?",
                new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public void saveCounty(County county)
    {
        if(county!=null)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put("county_name",county.getCountyName());
            contentValues.put("county_code",county.getCountyCode());
            contentValues.put("city_id",county.getCityId());
            sqLiteDatabase.insert("County",null,contentValues);
        }
    }

    public List<County> loadCounty(int cityId)
    {
        List<County> list=new ArrayList<County>();
        Cursor cursor=sqLiteDatabase.query("County",null,"city_id = ?",
                new String[]{String.valueOf(cityId)},null,null,null);
        if(cursor.moveToFirst())
        {
            do{
                County county=new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCityName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            }while (cursor.moveToNext());
        }
        if(cursor!=null)
        {
            cursor.close();
        }
        return list
                 ;
    }
}
