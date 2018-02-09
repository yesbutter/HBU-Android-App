package cn.edu.pku.zhangqixun.util;

import android.text.TextUtils;

import cn.edu.pku.zhangqixun.DataBase.WeatherDB;
import cn.edu.pku.zhangqixun.bean.City;
import cn.edu.pku.zhangqixun.bean.County;
import cn.edu.pku.zhangqixun.bean.Province;

/**
 * Created by 12968 on 2018/2/9.
 */

public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     */

    public synchronized static boolean handleProvinceResponse(WeatherDB weatherDB,String response)
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


}
