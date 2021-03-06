package com.example.a12968.myweather.Main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.a12968.myweather.Activities.Choose_City;
import com.example.a12968.myweather.Activities.Editor_Location;
import com.example.a12968.myweather.Adapter.CityFragmentAdapter;
import com.example.a12968.myweather.Bean.CityFragment;
import com.example.a12968.myweather.Bean.StringItem;
import com.example.a12968.myweather.DataBase.WeatherDB;
import com.example.a12968.myweather.R;
import com.example.a12968.myweather.Util.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final int QUERY_CITY = 0;
    private static final int EDITOR_LOCATION = 1;

    private WaveSwipeRefreshLayout waveSwipeRefreshLayout;
    private WeatherHelper weatherHelper = new WeatherHelper(this);
    private HashMap<String, String> hashMap = new HashMap<>();
    private static ArrayList<CityFragment> cityFragments = new ArrayList<>();

    private static CityFragmentAdapter cityFragmentAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initWaveSwipeRefreshLayout();
        initCityCode();
        initPermission();

        cityFragmentAdapter = new CityFragmentAdapter(getSupportFragmentManager(), cityFragments);
        initDateLoading();

        mViewPager = findViewById(R.id.main_viewpager);
        mViewPager.setAdapter(cityFragmentAdapter);
    }

    @SuppressLint("MissingSuperCall")
    public void onResume() {
        super.onResume();
//        initDateLoading();
    }

    public void pluslistener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Editor_Location.class);
        startActivityForResult(intent, EDITOR_LOCATION);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_查询城市:
                intent.setClass(this, Choose_City.class);
                startActivityForResult(intent, QUERY_CITY);
                break;
            case R.id.nav_自动定位:
                getNetWorkLocation();
                break;
            case R.id.nav_编辑地点:
                intent.setClass(this, Editor_Location.class);
                startActivityForResult(intent, EDITOR_LOCATION);
                break;
            case R.id.nav_编辑的话:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Tip1:实现了下滑刷新，如果没有城市让用户去添加城市" +
                        "  Tip2:添加城市可以侧滑，选择置顶或者删除，也实现了长按弹出对话框删除" +
                        "  \nTip3:自动定位可以显示位置，没有去添加或者显示天气，提示了一下地点" +
                        "  Tip4:嗯~ o(*￣▽￣*)o，发现了什么bug及时提给我！");
                builder.setTitle("Tip");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
                break;
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case QUERY_CITY:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    assert bundle != null;
                    String select_city = bundle.getString("weather_code");
                    weatherHelper.getWeather(select_city, showWeatherListener);
                }
                break;
            case EDITOR_LOCATION:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    assert bundle != null;
                    String string = bundle.getString("city_name");
                    weatherHelper.getWeather(hashMap.get(string), showWeatherListener, 1);
                }
                break;
            default:
                break;
        }
    }


    private WeatherHelper.ShowWeatherListener showWeatherListener = new WeatherHelper.ShowWeatherListener() {
        @Override
        public void showWeather() {
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

            Log.e("Show-Weather", sharedPreferences.getString("current_date", ""));
            Log.e("Show-Weather", sharedPreferences.getString("city_name", ""));
            Log.e("Show-Weather", sharedPreferences.getString("weather_desp", ""));
            Log.e("Show-Weather", sharedPreferences.getString("weather_code", ""));
            Log.e("Show-Weather", sharedPreferences.getString("temp1", ""));
            Log.e("Show-Weather", sharedPreferences.getString("temp2", ""));
            Log.e("Show-Weather", sharedPreferences.getString("publish_time", ""));

            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    };

    private void getNetWorkLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        final Location location = Location_Based_Services.getNetWorkLocation(this);
        if (location == null) {
            Util.makeToast(this, "NET打开失败");
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Address> addresses = Location_Based_Services.getAddress(location, MainActivity.this);
                    String string = addresses.get(0).getAddressLine(0);
                    string = string.replaceAll(addresses.get(0).getFeatureName(), "");
                    string = string.replaceAll(addresses.get(0).getAdminArea(), "");
                    string = string.replaceAll(addresses.get(0).getLocality(), "");
                    string = string.replaceAll("市", "");
                    string = string.replaceAll("县", "");
                    string = string.replaceAll("乡", "");
                    string = string.replaceAll("区", "");

                    Log.e(TAG, "run: " + hashMap.get(string));

                    Looper.prepare();

                    Toast.makeText(MainActivity.this, "你定位在" + string, Toast.LENGTH_LONG).show();
//
//                    addCityFragment(hashMap.get(string),0,System.currentTimeMillis());
//
//                    weatherHelper.getWeather(hashMap.get(string), showWeatherListener, 1);
                    Looper.loop();
                }
            }).start();
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("SetTextI18n")
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                    Log.d("TAG", String.valueOf(mViewPager.getCurrentItem()));
                    cityFragments.get(mViewPager.getCurrentItem()).setTextViewText(sharedPreferences.getString("current_date", "") + "\n"
                            + sharedPreferences.getString("city_name", "") + "\n"
                            + sharedPreferences.getString("weather_desp", "") + "\n"
                            + sharedPreferences.getString("weather_code", "") + "\n"
                            + sharedPreferences.getString("temp1", "") + "\n"
                            + sharedPreferences.getString("temp2", "") + "\n"
                            + sharedPreferences.getString("publish_time", ""));
                    Log.e(TAG, "PAGE" + Integer.toString(mViewPager.getCurrentItem()));
                    Log.e(TAG, "PAGE" + Integer.toString(cityFragmentAdapter.getCount()));
//                    textView.setText(sharedPreferences.getString("current_date", "") + "\n"
//                            + sharedPreferences.getString("city_name", "") + "\n"
//                            + sharedPreferences.getString("weather_desp", "") + "\n"
//                            + sharedPreferences.getString("weather_code", "") + "\n"
//                            + sharedPreferences.getString("temp1", "") + "\n"
//                            + sharedPreferences.getString("temp2", "") + "\n"
//                            + sharedPreferences.getString("publish_time", ""));

                    Util.makeToast(MainActivity.this, "Refresh success");
                default:
                    break;
            }
        }
    };

    private void initWaveSwipeRefreshLayout() {
        waveSwipeRefreshLayout = findViewById(R.id.main_swipe);
        waveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        waveSwipeRefreshLayout.setWaveColor(Color.argb(100, 0, 255, 255));
        waveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 更新が終了したらインジケータ非表示
                        // waveSwipeRefreshLayout.setRefreshing(false);
                        if (cityFragments.size() > 0) {
                            String cityname;
                            for (StringItem stringItem : CityFragment.stringItems) {
                                Log.e("Main", stringItem.toString());
                            }
                            cityname = CityFragment.stringItems.get(mViewPager.getCurrentItem()).getString();
//                            weatherHelper.getWeather(select_city, showWeatherListener);
                            weatherHelper.getWeather(hashMap.get(cityname), showWeatherListener, 1);

                        } else {

                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏虚拟按键栏
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE //防止点击屏幕时,隐藏虚拟按键栏又弹了出来
                            );

                            Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "请先选择城市", Snackbar.LENGTH_LONG);
                            snackbar.setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.setClass(MainActivity.this, Editor_Location.class);
                                    startActivityForResult(intent, EDITOR_LOCATION);

                                }
                            }).show();

                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                        }
                        waveSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

            }
        });
    }


    private void initCityCode() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getResources().openRawResource(R.raw.citycode));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s;
            while (true) {
                s = bufferedReader.readLine();
                if (s == null)
                    break;
                if (s.contains("=")) {
                    String b[] = s.split("=");
                    hashMap.put(b[1], b[0]);
                }
            }
        } catch (Exception e) {
            Log.e("TAG", e.toString());
            e.printStackTrace();
        }
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void initDateLoading() {
//        cityFragments.clear();
//        cityFragmentAdapter.notifyDataSetChanged();
        List<StringItem> list = WeatherDB.getWeatherDB(this).loadStringItem();
        for (StringItem stringItem : list) {
            cityFragments.add(CityFragment.newInstance(stringItem.getString(), stringItem.getTop(), stringItem.getTime()));
        }
        cityFragmentAdapter.Fragmentsort();
        cityFragmentAdapter.notifyDataSetChanged();
    }

    public static void addCityFragment(String cityName, int top, long time) {
        cityFragments.add(CityFragment.newInstance(cityName, top, time));
        cityFragmentAdapter.Fragmentsort();
        cityFragmentAdapter.notifyDataSetChanged();
    }

    public static void removeCityFragment(int position) {

        Log.d(TAG, "removeCityFragment: " + position);

//        if (cityFragments.size() == 1) {
//            cityFragments.remove(0);
//        } else {
//            if (position != 0) {
//                cityFragments.remove(0);
//            } else {
//                cityFragments.remove(1);
//            }
//        }
        cityFragments.remove(cityFragments.size() - 1);
        cityFragmentAdapter.notifyDataSetChanged();
    }

    public static void setTopFragment(int position, int top) {


        CityFragment.stringItems.get(position).setTop(top);
        cityFragmentAdapter.Fragmentsort();
        cityFragmentAdapter.notifyDataSetChanged();
    }

}
