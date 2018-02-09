package cn.edu.pku.zhangqixun.bean;

/**
 * Created by 12968 on 2018/2/9.
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
