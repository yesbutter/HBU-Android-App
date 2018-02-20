package com.example.a12968.myweather.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.a12968.myweather.Bean.City;
import com.example.a12968.myweather.Bean.County;
import com.example.a12968.myweather.Bean.Province;
import com.example.a12968.myweather.DataBase.WeatherDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by t-lidashao on 18-2-18.
 */

public class Utility {

    /**
     * 解析和处理服务器返回的省级数据
     */
    public synchronized static boolean handleProvinceResponse(WeatherDB weatherDB, String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allProvinces=response.split(",");
            if (allProvinces!=null&&allProvinces.length>0)
            {
                for(String string:allProvinces)
                {
                    String[] array=string.split("\\|");
                    Province province=new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCitiesResponse(WeatherDB weatherDB,String response,int provinceid)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allProvinces=response.split(",");
            if (allProvinces!=null&&allProvinces.length>0)
            {
                for(String string:allProvinces)
                {
                    String[] array=string.split("\\|");
                    City city=new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceid);
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     * @param weatherDB
     * @param response
     * @param cityid
     * @return
     */
    public static boolean handleCountierResponse(WeatherDB weatherDB,String response,int cityid)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allProvinces=response.split(",");
            if (allProvinces!=null&&allProvinces.length>0)
            {
                for(String string:allProvinces)
                {
                    String[] array=string.split("\\|");
                    County county=new County();
                    county.setCountyCode(array[0]);
                    county.setCityName(array[1]);
                    county.setCityId(cityid);
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的JSON数据，并将解析出的数据存储到本地
     * @param context
     * @param response
     */
    public static void handleWeatherResponse(Context context, String response)
    {
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONObject weatherInfo=jsonObject.getJSONObject("weatherinfo");
            String cityName=weatherInfo.getString("city");
            String weatherCode=weatherInfo.getString("cityid");
            String temp1=weatherInfo.getString("temp1");
            String temp2=weatherInfo.getString("temp2");
            String weatherDesp=weatherInfo.getString("weather");
            String publishTime=weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储天气信息
     * @param context 上下文对象
     * @param cityName 城市名字
     * @param weathercode 天气编码
     * @param temp1 温度
     * @param temp2 温度
     * @param weatherDesp 天气类型
     * @param publishTime 发布时间
     */
    public static void saveWeatherInfo(Context context,String cityName,String weathercode,String temp1,String temp2,String weatherDesp,String publishTime)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("weather_code",weathercode);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weather_desp",weatherDesp);
        editor.putString("publish_time",publishTime);
        editor.putString("current_date",simpleDateFormat.format(new Date()));
        editor.commit();
    }


}
