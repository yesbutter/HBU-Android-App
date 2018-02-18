package com.example.a12968.myweather.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.a12968.myweather.Activities.Choose_City;
import com.example.a12968.myweather.R;
import com.example.a12968.myweather.Util.Util;

import java.lang.ref.WeakReference;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final int QUERY_CITY = 0;
    private static String select_city = "090201";

    private WaveSwipeRefreshLayout waveSwipeRefreshLayout;
    private WeatherHelper weatherHelper = new WeatherHelper(this);




    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initWaveSwipeRefreshLayout();


        textView = findViewById(R.id.main_text_view);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_查询城市:
                intent.setClass(this, Choose_City.class);
                startActivityForResult(intent, QUERY_CITY);
                break;
            case R.id.nav_gallery:
                weatherHelper.setContext(this);
                weatherHelper.getWeather(select_city, showWeatherListener);


            default:
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void showWeather(SharedPreferences sharedPreferences) {
        textView.setText(sharedPreferences.getString("current_date", "") + "\n"
                + sharedPreferences.getString("city_name", "") + "\n"
                + sharedPreferences.getString("weather_desp", "") + "\n"
                + sharedPreferences.getString("weather_code", "") + "\n"
                + sharedPreferences.getString("temp1", "") + "\n"
                + sharedPreferences.getString("temp2", "") + "\n"
                + sharedPreferences.getString("publish_time", ""));
        Util.makeToast(this,"Refresh success");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case QUERY_CITY:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    select_city = bundle.getString("weather_code");
                    Log.e(TAG, select_city);
                }
                break;
            default:
                break;
        }
    }


    private WeatherHelper.ShowWeatherListener showWeatherListener=new WeatherHelper.ShowWeatherListener() {
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.this.showWeather(sharedPreferences);
                }
            });
        }
    };

    private void initWaveSwipeRefreshLayout() {
        waveSwipeRefreshLayout = findViewById(R.id.main_swipe);
        waveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        waveSwipeRefreshLayout.setWaveColor(Color.argb(100, 255, 0, 0));
        waveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                weatherHelper.getWeather(select_city, showWeatherListener);
                waveSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
