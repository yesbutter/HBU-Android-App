package com.example.a12968.myweather.Main;

/**
 * Help user to get the location
 * Created by t-lidashao on 18-2-18.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import java.io.IOException;
import java.util.List;
import java.util.Locale;



public class Location_Based_Services {

    private static final long REFRESH_TIME=5000L;
    private static final float METER_POSITION=0.0f;
    private static ILocationListener mLocationListener;
    private static LocationListener listener = new MyLocationListener();

    private static class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {

            if(mLocationListener!=null)
            {
                mLocationListener.DisplayLocation(location);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    public static LocationManager getLocationManger(Context context)
    {
        return (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    }


    public static Location getGPSLocation(@Nullable Context context)
    {
        Location location=null;
        LocationManager manager=getLocationManger(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            location=manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return location;
    }

    public static Location getNetWorkLocation(Context context)
    {
        Location location=null;
        LocationManager manager=getLocationManger(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            location=manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return  location;
    }

    public static Location getBestLocation(Context context, Criteria criteria)
    {
        Location location;
        LocationManager manager=getLocationManger(context);
        if(criteria==null)
        {
            criteria=new Criteria();
        }
        String provider=manager.getBestProvider(criteria,true);
        if(TextUtils.isEmpty(provider))
        {
            location=getNetWorkLocation(context);
        }
        else {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            location=manager.getLastKnownLocation(provider);
        }
        return  location;
    }

    /**
     * 定位监听
     * @param context
     * @param provider
     * @param locationListener
     */
    public static void addLocationListener(Context context,String provider,ILocationListener locationListener)
    {
        addLocationListener(context,provider,REFRESH_TIME,METER_POSITION,locationListener);
    }


    /**
     *
     */
    public static void addLocationListener(Context context,String provider,long time,float meter,ILocationListener locationListener)
    {
        if (locationListener != null) {
            mLocationListener = locationListener;
        }
        if (listener == null) {
            listener = new MyLocationListener();
        }
        LocationManager manager = getLocationManger(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.requestLocationUpdates(provider, time, meter, listener);
    }






    public static List<Address> getAddress(Location location, Context context) {
        List<Address> addresses = null;
        try {
            if (location != null) {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                addresses = geocoder.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }



    public interface ILocationListener{
        void DisplayLocation(Location location);
    }
}
