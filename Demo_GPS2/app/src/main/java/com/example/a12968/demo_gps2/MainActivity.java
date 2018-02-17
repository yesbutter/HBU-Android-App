package com.example.a12968.demo_gps2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button GPS, Network;
    private TextView LBSshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        initListener();
        initPermission();
    }

    private void initview() {
        GPS = findViewById(R.id.GPS);
        Network = findViewById(R.id.NETwork);
        LBSshow = findViewById(R.id.LBSshow);
    }

    private void initListener() {
        GPS.setOnClickListener(this);
        Network.setOnClickListener(this);
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void GPSisopen() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请打开GPS", Toast.LENGTH_SHORT);
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("为了获取定位服务，请先打开GPS");
            dialog.setPositiveButton("设置", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 0);
                }
            });
            dialog.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.show();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.GPS:
                initPermission();
                GPSisopen();
                getGPSLocation();
                LBSshow.setText("GPS Result");
                break;
            case R.id.NETwork:
                initPermission();
                //GPSisopen();//基站也需要GPS
                getNetWorkLocation();
                LBSshow.setText("NetWork Result");
                break;
            default:

                break;
        }
    }

    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    Bundle bundle=msg.getData();
                    String string=bundle.getString("Address");
                    LBSshow.setText(LBSshow.getText()+"\n"+string);
                    break;
                default:

                    break;
            }
        }
    };

    private void getGPSLocation() {
        Location location = Location_Based_Services.getGPSLocation(this);
        if (location == null) {

            Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
            Location_Based_Services.addLocationListener(this, LocationManager.GPS_PROVIDER, new Location_Based_Services.ILocationListener() {
                @Override
                public void DisplayLocation(Location location) {
                    if (location == null) {
                        Toast.makeText(MainActivity.this, "Sorry,The Location is not found!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "GPS \nlocation:latitude=" + location.getLatitude() + "\nGPS location:longgitude" + location.getLongitude()
                                , Toast.LENGTH_LONG).show();
                        showAddress(location);
                    }
                }
            });
        } else {
            Toast.makeText(this, "GPS \nlocation:latitude=" + location.getLatitude() + "\nGPS location:longgitude" + location.getLongitude()
                    , Toast.LENGTH_LONG).show();
            showAddress(location);
        }
    }

    private void getNetWorkLocation() {
        Location location = Location_Based_Services.getNetWorkLocation(this);
        if (location == null) {
            Toast.makeText(this, "Net Location is null", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "NetWork \nlocation:latitude=" + location.getLatitude() + "\nNewWork location:longgitude" + location.getLongitude()
                    , Toast.LENGTH_LONG).show();
            showAddress(location);
        }
    }


    private void showAddress(Location location)
    {
        AddressRunnable addressRunnable=new AddressRunnable(location);
        Thread thread=new Thread(addressRunnable);
        thread.start();
    }

    private List<Address> getAddress(Location location) {
        List<Address> addresses = null;
        try {
            if (location != null) {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                addresses = geocoder.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    private class AddressRunnable implements Runnable {
        private Location location = null;
        private List<Address> addresses = null;

        AddressRunnable(Location location) {
            this.location = location;
        }

        protected List<Address> getAddresses() {
            return this.addresses;
        }

        @Override
        public void run() {
            if (location != null) {
                addresses = getAddress(location);
                Message message=new Message();
                Bundle bundle=new Bundle();
                Log.e("Str",addresses.toString());
                bundle.putString("Address",addresses.get(0).getAdminArea()+addresses.get(0).getLocality()+addresses.get(0).getFeatureName());
                message.setData(bundle);
                message.what=1;
                handler.sendMessage(message);
            }
        }
    }
}
