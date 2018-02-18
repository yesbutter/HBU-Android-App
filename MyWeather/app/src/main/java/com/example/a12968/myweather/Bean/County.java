package com.example.a12968.myweather.Bean;

/**
 * Created by t-lidashao on 18-2-18.
 */

public class County {
    private int id;
    private String countyName;
    private String countyCode;
    private  int cityId;

    public void setId(int id)
    {
        this.id=id;
    }

    public void setCityName(String countyName)
    {
        this.countyName= countyName;
    }

    public void setCountyCode(String countyCode)
    {
        this.countyCode = countyCode;
    }

    public void setCityId(int cityId)
    {
        this.cityId = cityId;
    }

    public int getId()
    {
        return this.id;
    }

    public String getCountyName()
    {
        return this.countyName;
    }

    public String getCountyCode()
    {
        return this.countyCode;
    }

    public int getCityId()
    {
        return this.cityId;
    }
}
