package com.example.a12968.myweather.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.a12968.myweather.Bean.City;
import com.example.a12968.myweather.Bean.County;
import com.example.a12968.myweather.Bean.Province;
import com.example.a12968.myweather.DataBase.WeatherDB;
import com.example.a12968.myweather.Main.MainActivity;
import com.example.a12968.myweather.R;
import com.example.a12968.myweather.Util.HttpUtil;
import com.example.a12968.myweather.Util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by t-lidashao on 18-2-18.
 */

public class Choose_City extends Activity{
    public static final int LEVERL_PROVINCE = 0;
    public static final int LEVERL_CITY = 1;
    public static final int LEVERL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView textView;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private WeatherDB weatherDB;
    private List<String> datalist = new ArrayList<String>();
    private static final String TAG="Choose_City";

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
        setContentView(R.layout.choose_city);
        listView = (ListView) findViewById(R.id.choose_city_list_view);
        textView = (TextView) findViewById(R.id.choose_city_title_text);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datalist);
        listView.setAdapter(arrayAdapter);
        weatherDB = WeatherDB.getWeatherDB(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVERL_PROVINCE) {
                    selectedProvince = provinceList.get(i);
                    queryCities();
                } else if (currentLevel == LEVERL_CITY) {
                    selectedCity = cityList.get(i);
                    queryCounties();
                } else if (currentLevel == LEVERL_COUNTY) {
                    selectedCounty = countyList.get(i);
                    Log.e(TAG, "onItemClick: "+ "你选择了：" + selectedProvince.getProvinceName() + " "
                            + selectedCity.getCityName() + " " + selectedCounty.getCountyName());

                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("weather_code",selectedCounty.getCountyCode());
                    bundle.putString("city_name",selectedCounty.getCountyName());
                    intent.putExtras(bundle);
                    //intent.setClass(Choose_City.this,MainActivity.class);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
        queryProvince();
    }


    /**
     * 访问省级城市数据
     */
    private void queryProvince() {
        provinceList = weatherDB.loadProvince();
        if (provinceList.size() > 0) {
            datalist.clear();
            for (Province province : provinceList) {

                datalist.add(province.getProvinceName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            textView.setText("中国");
            currentLevel = LEVERL_PROVINCE;
        } else {
            queryFromServer(null, "province");
        }
    }

    /**
     * 询问市级城市数据
     */
    private void queryCities() {
        cityList = weatherDB.loadCity(selectedProvince.getId());
        if (cityList.size() > 0) {
            datalist.clear();
            for (City city : cityList) {
                datalist.add(city.getCityName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            textView.setText(selectedProvince.getProvinceName());
            currentLevel = LEVERL_CITY;
        } else {
            queryFromServer(selectedProvince.getProvinceCode(), "city");
        }
    }

    /**
     * 询问乡村城市数据
     */
    private void queryCounties() {
        countyList = weatherDB.loadCounty(selectedCity.getId());
        if (countyList.size() > 0) {
            datalist.clear();
            for (County county : countyList) {
                datalist.add(county.getCountyName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            textView.setText(selectedCity.getCityName());
            currentLevel = LEVERL_COUNTY;
        } else {
            queryFromServer(selectedCity.getCityCode(), "county");
        }
    }


    /**
     * 根据代号去服务器查找
     */
    private void queryFromServer(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(weatherDB, response);
                } else if ("city".equals(type)) {
                    result = Utility.handleCitiesResponse(weatherDB, response, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountierResponse(weatherDB, response, selectedCity.getId());
                }
                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvince();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
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
                        Log.e("Choose_City","加载失败");
                    }
                });
            }
        });
    }


    /**
     * Show progress dialog
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    /**
     * close progress dialog
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * handle the back button
     */
    public void onBackPressed() {
        if (currentLevel == LEVERL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVERL_CITY) {
            queryProvince();
        } else {
            finish();
        }
    }

}
