package cn.edu.pku.zhangqixun.miniweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.pku.zhangqixun.app.ChooseAreaActivity;
import cn.edu.pku.zhangqixun.bean.TodayWeather;
import cn.edu.pku.zhangqixun.util.HttpUtil;
import cn.edu.pku.zhangqixun.util.NetUtil;
import cn.edu.pku.zhangqixun.util.Utility;

/**
 * Created by zhangqixun on 16/7/4.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private static final int UPDATE_TODAY_WEATHER = 1;

    private static String select_county_code = "090201";

    private ImageView mUpdateBtn;

    private ImageView mCitySelect;

    private Context mContext =this;

    private DrawerLayout drawerLayout;
    public NavigationView navView;
    private TextView cityTv, timeTv, humidityTv, weekTv, pmDataTv, pmQualityTv,
            temperatureTv, climateTv, windTv, city_name_Tv, mytextview;
    private ImageView weatherImg, pmImg;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
                default:
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mytextview = (TextView) findViewById(R.id.mytextview);
        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
            Log.d("myWeather", "网络OK");
            Toast.makeText(MainActivity.this, "网络OK！", Toast.LENGTH_LONG).show();
        } else {
            Log.d("myWeather", "网络挂了");
            Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
        }

        mCitySelect = (ImageView) findViewById(R.id.title_city_manager);
        mCitySelect.setOnClickListener(this);

        initView();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {//抽屉菜单监听 2017.12.27
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Uri uri;
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.nav_selectcity:
                        Intent i = new Intent();
                        i.setClass(mContext,ChooseAreaActivity.class);
                        startActivityForResult(i, 0);// 0 用于识别第二个页面返回值
                        break;
                    case R.id.nav_index:
                        break;
                    case R.id.nav_introduce:
                        break;
                    case R.id.nav_news:
                        break;
                    case R.id.nav_university:
                        uri = Uri.parse("http://www.baidu.com");
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    void initView() {
        city_name_Tv = (TextView) findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        humidityTv = (TextView) findViewById(R.id.humidity);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);

        city_name_Tv.setText("N/A");
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.title_city_manager) {
           drawerLayout.openDrawer(GravityCompat.START);
        }

        if (view.getId() == R.id.title_update_btn) {

            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络OK");
                queryWeatherCode(select_county_code);
            } else {
                Log.d("myWeather", "网络挂了");
                Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * @param countyCode
     */
    private void queryWeatherCode(String countyCode) {
         //final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + countyCode;
         final String address = "http://www.weather.com.cn/data/list3/city" + countyCode+".xml";
         queryFromServer(address,"countyCode");

        //queryFromServer(address1,"weatherCode");
        //Log.d("myWeather", address);
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con = null;
                TodayWeather todayWeather = null;
                try {
                    URL url = new URL(address);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        response.append(str);
                        Log.d("myWeather", str);
                    }
                    String responseStr = response.toString();
                    Log.d("myWeather", responseStr);

                    todayWeather = parseXML(responseStr);
                    if (todayWeather != null) {
                        Log.d("myWeather", todayWeather.toString());

                        Message msg = new Message();
                        msg.what = UPDATE_TODAY_WEATHER;
                        msg.obj = todayWeather;
                        mHandler.sendMessage(msg);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
            }
        }).start();*/
    }

    private void queryWeatherThread(String countyCode)
    {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + countyCode;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con = null;
                TodayWeather todayWeather = null;
                try {
                    URL url = new URL(address);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);

                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        response.append(str);
                        Log.d("myWeather", str);
                    }
                    String responseStr = response.toString();
                    Log.d("myWeather", responseStr);

                    todayWeather = parseXML(responseStr);
                    if (todayWeather != null) {
                        Log.d("myWeather", todayWeather.toString());

                        Message msg = new Message();
                        msg.what = UPDATE_TODAY_WEATHER;
                        msg.obj = todayWeather;
                        mHandler.sendMessage(msg);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
            }
        }).start();
    }

    private void queryWeatherInfo(String weatherCode)
    {
        Log.e("WEATHERCODE",weatherCode);
        String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        queryWeatherThread(weatherCode);
        queryFromServer(address,"weatherCode");
    }

    private void queryFromServer(final String address,final String type)
    {
        HttpUtil.sendHttpRequest(address, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                /**
                 * 处理服务器返回的天气信息
                 */
                if("countyCode".equals(type))
                {
                    if(!TextUtils.isEmpty(response))
                    {
                        String[] array=response.split("\\|");
                        if(array!=null&&array.length==2)
                        {
                            String weatherCode=array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                }
                else if("weatherCode".equals(type))
                {
                    Utility.handleWeatherResponse(MainActivity.this,response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("Weather_code0","同步失败");

            }
        });
    }

    private void showWeather()
    {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

//        city_name_Tv.setText(sharedPreferences.getString("city_name","") + "天气");
//        cityTv.setText(sharedPreferences.getString("city_name",""));
//        timeTv.setText(sharedPreferences.getString("publish_time","") + "发布");
//        humidityTv.setText("湿度：" + todayWeather.getShidu());
//        pmDataTv.setText(todayWeather.getPm25());
//        pmQualityTv.setText(todayWeather.getQuality());
//        weekTv.setText(todayWeather.getDate());
//        temperatureTv.setText(sharedPreferences.getString("temp1","") + "~" + sharedPreferences.getString("temp2",""));
//        climateTv.setText(sharedPreferences.getString("weather_desp",""));
//        windTv.setText("风力:" + todayWeather.getFengli());

        Log.e("Show-Weather",sharedPreferences.toString());
        Log.e("Show-Weather",sharedPreferences.getString("current_date",""));
        Log.e("City-Name",sharedPreferences.getString("city_name",""));
        Log.e("weather_code",sharedPreferences.getString("weather_desp",""));
        Log.e("weather_code",sharedPreferences.getString("weather_code",""));
        Log.e("temp1",sharedPreferences.getString("temp1",""));
        Log.e("temp2",sharedPreferences.getString("temp2",""));
        Log.e("publish_time",sharedPreferences.getString("publish_time",""));
    }

    private TodayWeather parseXML(String xmldata) {
        TodayWeather todayWeather = null;
        int fengxiangCount = 0;
        int fengliCount = 0;
        int dateCount = 0;
        int highCount = 0;
        int lowCount = 0;
        int typeCount = 0;
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = fac.newPullParser();
            xmlPullParser.setInput(new StringReader(xmldata));
            int eventType = xmlPullParser.getEventType();
            Log.d("myWeather", "parseXML");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    // 判断当前事件是否为文档开始事件
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    // 判断当前事件是否为标签元素开始事件
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("resp")) {
                            todayWeather = new TodayWeather();
                        }
                        if (todayWeather != null) {
                            if (xmlPullParser.getName().equals("city")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setCity(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("updatetime")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setUpdatetime(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("shidu")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setShidu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setWendu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("pm25")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setPm25(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("quality")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setQuality(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setFengxiang(xmlPullParser.getText());
                                fengxiangCount++;
                            } else if (xmlPullParser.getName().equals("fengli") && fengliCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setFengli(xmlPullParser.getText());
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setDate(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setHigh(xmlPullParser.getText().substring(2).trim());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setLow(xmlPullParser.getText().substring(2).trim());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setType(xmlPullParser.getText());
                                typeCount++;
                            }
                        }

                        break;


                    // 判断当前事件是否为标签元素结束事件
                    case XmlPullParser.END_TAG:
                        break;
                }
                // 进入下一个元素并触发相应事件
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todayWeather;
    }

    void updateTodayWeather(TodayWeather todayWeather) {
        city_name_Tv.setText(todayWeather.getCity() + "天气");
        cityTv.setText(todayWeather.getCity());
        timeTv.setText(todayWeather.getUpdatetime() + "发布");
        humidityTv.setText("湿度：" + todayWeather.getShidu());
        pmDataTv.setText(todayWeather.getPm25());
        pmQualityTv.setText(todayWeather.getQuality());
        weekTv.setText(todayWeather.getDate());
        temperatureTv.setText(todayWeather.getHigh() + "~" + todayWeather.getLow());
        climateTv.setText(todayWeather.getType());
        windTv.setText("风力:" + todayWeather.getFengli());
        Toast.makeText(MainActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String string = bundle.getString("weather_code");
                    Log.e("weather-code", string);
                    select_county_code = string;
                    queryWeatherCode(select_county_code);
                }
                break;
            default:
                break;
        }
    }


}