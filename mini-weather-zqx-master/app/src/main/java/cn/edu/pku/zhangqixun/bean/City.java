package cn.edu.pku.zhangqixun.bean;

/**
 * Created by zhangqixun on 16/7/7.
 */
public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private  int provinceId;

    public void setId(int id)
    {
        this.id=id;
    }

    public void setCityName(String cityName)
    {
        this.cityName=cityName;
    }

    public void setCityCode(String cityCode)
    {
        this.cityCode=cityCode;
    }

    public void setProvinceId(int provinceId)
    {
        this.provinceId=provinceId;
    }

    public int getId()
    {
        return this.id;
    }

    public String getCityName()
    {
        return this.cityName;
    }

    public String getCityCode()
    {
        return this.cityCode;
    }

    public int getProvinceId()
    {
        return this.provinceId;
    }
}
