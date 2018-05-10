package com.example.qq1296821114.monitor;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import static android.content.ContentValues.TAG;


public class NeNotificationService extends AccessibilityService {

	private static String qqpimsecure = "com.tencent.qqpimsecure";
	//public static String TAG = "NeNotificationService";
    	private LinearLayout rootLayout;
    private NotificationManager manager;
    public static final int TYPE_Normal = 1;

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED&&event.getEventType() !=AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.e(TAG, "onAccessibilityEvent: " + event.toString() + "\n");
            String string=event.toString();
            String ans="";
                int begin = string.indexOf("Text:");
                for (int i = begin; i < string.length(); i++) {
                    if (string.charAt(i) == '[') {
                        while (string.charAt(++i) != ']')
                            ans += string.charAt(i);
                        break;
                    }
                }
                Log.e(TAG, "ans: " + ans);
            if(ans.contains("支付凭证")||ans.contains("支出")||ans.contains("付款")) {
                manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                builder.setTicker("记账提醒");
                builder.setContentTitle("记账");
                builder.setContentText(ans);
                builder.setSubText("ans:" + ans);
                builder.setAutoCancel(true);
                builder.setNumber(2);
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                Notification notification = builder.build();
                manager.notify(TYPE_Normal, notification);
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
