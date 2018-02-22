package com.example.a12968.myweather.Bean;

/**
 *
 * Created by t-lidashao on 18-2-18.
 */

public class Province {
    private int id;
    private String provinceName;
    private String provinceCode;

    public void setId(int id)
    {
        this.id=id;
    }

    public void setProvinceName(String provinceName)
    {
        this.provinceName=provinceName;
    }

    public void setProvinceCode(String provinceCode)
    {
        this.provinceCode=provinceCode;
    }


    public int getId()
    {
        return this.id;
    }

    public String getProvinceName()
    {
        return this.provinceName;
    }

    public String getProvinceCode()
    {
        return this.provinceCode;
    }

}
