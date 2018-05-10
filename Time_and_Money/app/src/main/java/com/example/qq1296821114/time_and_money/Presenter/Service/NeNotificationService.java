package com.example.qq1296821114.time_and_money.Presenter.Service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.Presenter.Activity.MainActivity;
import com.example.qq1296821114.time_and_money.R;

import java.text.DecimalFormat;

import static android.content.ContentValues.TAG;


public class NeNotificationService extends AccessibilityService {

    private NotificationManager manager;
    public static final int TYPE_Normal = 1;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.e(TAG, "onAccessibilityEvent: " + event.toString() + "\n");
            String string = event.toString();
            String ans = "";
            int begin = string.indexOf("Text:");
            for (int i = begin; i < string.length(); i++) {
                if (string.charAt(i) == '[') {
                    while (string.charAt(++i) != ']')
                        ans += string.charAt(i);
                    break;
                }
            }
            if (ans.contains("支付凭证")) {
                manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                builder.setContentTitle("记账提醒");
                builder.setContentText(ans);
                builder.setAutoCancel(true);
                builder.setSmallIcon(R.drawable.ic_savemoney);
                Notification notification = builder.build();
                manager.notify(TYPE_Normal, notification);
            } else if (ans.contains("支出") || ans.contains("付款")) {
                String m = "";
                double money;
                Log.e(TAG, ":" + m);

                int left = ans.indexOf("一笔");
                int right = ans.indexOf("元");
                m = ans.substring(left, right);
                m = m.replace("一笔", "");
                Log.e(TAG, ":" + m);
                money = Double.parseDouble(m);
                Log.e(TAG, ":" + money);
//                Log.e(TAG, "onAccessibilityEvent: "+money );
                MainActivity.saveMoney(new Money(money, "支付宝",R.drawable.ic_aunt));
                Toast.makeText(this, "已记账！", Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    protected void onServiceConnected() {

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;
        setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {

    }

}
