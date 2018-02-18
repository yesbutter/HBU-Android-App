package com.example.a12968.myweather.Main;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.a12968.myweather.Util.HttpUtil;
import com.example.a12968.myweather.Util.Utility;

/**
 * Created by t-lidashao on 18-2-18.
 */

public class WeatherHelper {
    private String weatherinfo;
    private Context context;


    public WeatherHelper(Context context) {
        this.context=context;
    }


    public void setContext(Context context)
    {
        this.context=context;
    }

    public void getWeather(String countyID,final ShowWeatherListener showWeatherListener)
    {
        queryWeatherCode(countyID,showWeatherListener);
    }

    private void queryWeatherCode(String countyCode,final ShowWeatherListener showWeatherListener) {
        final String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";

        queryWeatherServer(address, "countyCode",showWeatherListener);
    }


    private void queryWeatherInfo(String weatherCode,final ShowWeatherListener showWeatherListener) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryWeatherServer(address, "weatherCode",showWeatherListener);
    }

    private void queryWeatherServer(final String address, final String type,final ShowWeatherListener showWeatherListener) {

        HttpUtil.sendHttpRequest(address, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode,showWeatherListener);
                        }
                    }
                }
                if ("weatherCode".equals(type)) {
                    Utility.handleWeatherResponse(context, response);
                    showWeatherListener.showWeather();
                }

            }

            @Override
            public void onError(Exception e) {
                Log.e("WeatherHelper","获取信息失败");

            }
        });
    }


    public interface ShowWeatherListener{
        void showWeather();
    }
//    private void showWeather() {
//        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
//
//        Log.e("Show-Weather",sharedPreferences.toString());
//        Log.e("Show-Weather",sharedPreferences.getString("current_date",""));
//        Log.e("Show-Weather",sharedPreferences.getString("city_name",""));
//        Log.e("Show-Weather",sharedPreferences.getString("weather_desp",""));
//        Log.e("Show-Weather",sharedPreferences.getString("weather_code",""));
//        Log.e("Show-Weather",sharedPreferences.getString("temp1",""));
//        Log.e("Show-Weather",sharedPreferences.getString("temp2",""));
//        Log.e("Show-Weather",sharedPreferences.getString("publish_time",""));
//    }


}
