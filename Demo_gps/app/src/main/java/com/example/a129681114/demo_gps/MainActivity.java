package com.example.a129681114.demo_gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.getDefault;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btnstartGPS;
    private TextView tvMapInfo;
    private List<Address> addresses = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_base_services);
        mContext = this;
        btnstartGPS = findViewById(R.id.startbaiduSDK);
        tvMapInfo = findViewById(R.id.position_text_view);
        btnstartGPS.setOnClickListener(this);

        List<String> permissionList = new ArrayList<>();// Permission array list , request permissions in one array.
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);//request all permissions
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startbaiduSDK:
                System.out.println("我被调用了");
                startBaiduMap();
                break;
            default:
                break;
        }
    }

    private void startBaiduMap() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "权限不够", Toast.LENGTH_LONG).show();
            System.out.println("权限不够");
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(mContext.LOCATION_SERVICE);

        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        upLoadInfor(location);


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 8, new LocationListener() {

            @Override
            /*当地理位置发生改变的时候调用*/
            public void onLocationChanged(Location location) {
                Log.e("GPS_SERVICES", "&#x4f4d;&#x7f6e;&#x4fe1;&#x606f;&#x53d1;&#x751f;&#x6539;&#x53d8;");
                upLoadInfor(location);

            }

            /* 当状态发生改变的时候调用*/
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("GPS_SERVICES", "状态信息发生改变");

            }

            /*当定位者启用的时候调用*/
            @Override
            public void onProviderEnabled(String s) {
                Log.d("TAG", "onProviderEnabled: ");

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("TAG", "onProviderDisabled: ");
            }
        });
    }

    private List<Address> getAddress(Location location) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(this, Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Handler handler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    tvMapInfo.setText(tvMapInfo.getText()+"\n"+addresses.toString());
                    break;
                default:
                    break;
            }
        }
    };
    private void upLoadInfor(Location location) {
        if (location != null) {
            double Longitude = location.getLongitude();
            double Latitude = location.getLatitude();

            Log.e("Location Based Services", "upLoadInfor: " + location.toString());
            tvMapInfo.setText("经度:" + Longitude + "\n纬度：" + Latitude + "\n速度:" + location.getSpeed());
        } else {
            List<String> permissionList = new ArrayList<>();// Permission array list , request permissions in one array.
            if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED){
                permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
            }
            if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if(!permissionList.isEmpty()){
                String[] permissions = permissionList.toArray(new String[permissionList.size()]);
                ActivityCompat.requestPermissions(MainActivity.this,permissions,1);//request all permissions
            }

            LocationManager locationManager = (LocationManager) getSystemService(mContext.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            Toast.makeText(this, "Sorry，请尽量在室外测试！！！", Toast.LENGTH_LONG).show();

        }
        final Location finalLocation = location;
        new Thread (new Runnable() {
            @Override
            public void run() {
               // tvMapInfo.setText(tvMapInfo.getText()+"\nHell new Thread");
                Log.e("Run","A new Thread");
                try {
                    final Location location1= finalLocation;
                    addresses= getAddress(location1
                    );
                    if (addresses != null) {
                        Log.e("run: ", addresses.toString());
                        Message message=new Message();
                        message.what=1;
                        handler.sendMessage(message);

                    }
                }catch (Exception e)
                {
                    Log.e("Exception","ERRPOR");
                }
            }

        }).start();
    }


}

