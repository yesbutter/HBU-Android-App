package com.example.qq1296821114.alarmmanager;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;


/**
 * Created by 12968 on 2018/4/10.
 */

public class RemindReceiver extends BroadcastReceiver {
    private NotificationManager manager;
    public static final int TYPE_Normal = 1;


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "收到消息", Toast.LENGTH_SHORT).show();

        //MediaPlayer.create(context, R.raw.remind).start();

        Bundle bundle = intent.getExtras();
        intent = new Intent(context, RemindActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        showNormal(context);
        context.startActivity(intent);

    }

    private void showNormal(Context context) {
       // Intent intent = new Intent(context, RemindActivity.class);//这里是点击Notification 跳转的界面，可以自己选择
        //PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("简单Notification");
        builder.setContentTitle("标题");
        builder.setContentText("通知内容");
        builder.setSubText("这里显示的是通知第三行内容！");
        builder.setAutoCancel(true);
        builder.setNumber(2);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setLargeIcon(BitmapFactory.decodeResource(R.drawable.ic_launcher_background));

        //PendingIntent pIntent = PendingIntent.getActivity(context, 1, intent, 0);
        // builder.setContentIntent(pIntent);

        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manager.notify(TYPE_Normal, notification);
    }


}