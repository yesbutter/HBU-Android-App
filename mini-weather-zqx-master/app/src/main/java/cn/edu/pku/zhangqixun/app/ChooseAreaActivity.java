package cn.edu.pku.zhangqixun.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.zhangqixun.DataBase.WeatherDB;
import cn.edu.pku.zhangqixun.bean.City;
import cn.edu.pku.zhangqixun.bean.County;
import cn.edu.pku.zhangqixun.bean.Province;
import cn.edu.pku.zhangqixun.miniweather.MainActivity;
import cn.edu.pku.zhangqixun.miniweather.R;
import cn.edu.pku.zhangqixun.util.HttpUtil;
import cn.edu.pku.zhangqixun.util.Utility;

/**
 * Created by 12968 on 2018/2/10.
 */

public class ChooseAreaActivity extends Activity {
    public static final int LEVERL_PROVINCE=0;
    public static final int LEVERL_CITY=1;
    public static final int LEVERL_COUNTY=2;

    private ProgressDialog progressDialog;
    private TextView textView;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private WeatherDB weatherDB;
    private List<String> datalist=new ArrayList<String>();
    /**
     * Province list
     */
    private List<Province> provinceList;
    /**
     * City list
     */
    private List<City> cityList;
    /**
     * County list
     */
    private List<County> countyList;
    /**
     * Selected province and city and county.
     */
    private Province selectedProvince;
    private City selectedCity;
    private County selectedCounty;

    private int currentLevel;

    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView= (ListView) findViewById(R.id.list_view);
        textView= (TextView) findViewById(R.id.title_text);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datalist);
        listView.setAdapter(arrayAdapter);
        weatherDB=WeatherDB.getWeatherDB(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentLevel==LEVERL_PROVINCE)
                {
                    selectedProvince=provinceList.get(i);
                    queryCities();
                }
                else if(currentLevel==LEVERL_CITY)
                {
                    selectedCity=cityList.get(i);
                    queryCounties();
                }
                else if(currentLevel==LEVERL_COUNTY)
                {
                    selectedCounty=countyList.get(i);
                    queryCounties();
                }
            }
        });
        queryProvince();
    }


    private void queryProvince()
    {
        provinceList=weatherDB.loadProvince();
        if(provinceList.size()>0)
        {
            datalist.clear();
            for(Province province:provinceList)
            {
                datalist.add(province.getProvinceName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            textView.setText("中国");
            currentLevel=LEVERL_PROVINCE;
        }
        else
        {
            queryFromServer(null,"province");
        }
    }

    private void queryCities()
    {
        cityList=weatherDB.loadCity(selectedProvince.getId());
        if(cityList.size()>0)
        {
            datalist.clear();
            for(City city:cityList)
            {
                datalist.add(city.getCityName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            textView.setText(selectedProvince.getProvinceName());
            currentLevel=LEVERL_CITY;
        }
        else
        {
            queryFromServer(selectedProvince.getProvinceCode(),"city");
        }
    }

    private void queryCounties()
    {
        if(currentLevel==LEVERL_COUNTY)
        {
            Toast.makeText(ChooseAreaActivity.this,"你选择了："+selectedProvince.getProvinceName()+" "+selectedCity.getCityName()+" "+selectedCounty.getCountyName(),Toast.LENGTH_SHORT).show();
        }
        countyList=weatherDB.loadCounty(selectedCity.getId());
        if(countyList.size()>0)
        {
            datalist.clear();
            for (County county:countyList)
            {
                datalist.add(county.getCountyName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            textView.setText(selectedCity.getCityName());
            currentLevel=LEVERL_COUNTY;
        }
        else
        {
            queryFromServer(selectedCity.getCityCode(),"county");
        }
    }

    /**
     * 根据代号去服务器查找
     */
    private void queryFromServer(final String code,final String type)
    {
        String address;
        if(!TextUtils.isEmpty(code))
        {
            address="http://www.weather.com.cn/data/list3/city"+code+".xml";
        }
        else
        {
            address="http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result=false;
                if("province".equals(type))
                {
                    result= Utility.handleProvinceResponse(weatherDB,response);
                }
                else if ("city".equals(type))
                {
                    result=Utility.handleCitiesResponse(weatherDB,response,selectedProvince.getId());
                }
                else if("county".equals(type))
                {
                    result=Utility.handleCountierResponse(weatherDB,response,selectedCity.getId());
                }
                if(result)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type))
                            {
                                queryProvince();
                            }
                            else if ("city".equals(type))
                            {
                                queryCities();
                            }
                            else if("county".equals(type))
                            {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * Show progress dialog
     */
    private void showProgressDialog() {
        if(progressDialog==null)
        {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
        }
    }

    public void onBackPressed()
    {
        if(currentLevel==LEVERL_COUNTY)
        {
            queryCities();
        }
        else if(currentLevel==LEVERL_CITY)
        {
            queryProvince();
        }
        else
        {
            finish();
        }
    }

}
