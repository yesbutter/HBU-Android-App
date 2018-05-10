package com.example.qq1296821114.alarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by 12968 on 2018/4/10.
 */
public class AlarmManageService {
    public static AlarmManager alarmManager;
    private static String TAG = "AlarmManageService";
    public static void addAlarm(Context context, int requestCode, Bundle bundle, int second){
        Intent intent = new Intent(context,RemindReceiver.class);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND,second);
        //注册新提醒
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

    }
}