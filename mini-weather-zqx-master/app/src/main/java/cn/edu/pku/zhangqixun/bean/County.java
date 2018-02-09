package cn.edu.pku.zhangqixun.bean;

/**
 * Created by 12968 on 2018/2/9.
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

    public String getCityName()
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
