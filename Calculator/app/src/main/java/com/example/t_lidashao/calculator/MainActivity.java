package com.example.t_lidashao.calculator;


import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.t_lidashao.calculator.R.menu.menu;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private  Toolbar toolbar;
    public static final int TYPE_Normal = 1;
    private NotificationManager manger;

    private void simpleNotify(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //Ticker是状态栏显示的提示

        builder.setTicker("简单Notification");

        //第一行内容  通常作为通知栏标题

        builder.setContentTitle("标题");

        //第二行内容 通常是通知正文

        builder.setContentText("通知内容");

        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示

        builder.setSubText("这里显示的是通知第三行内容！");

        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息

        //builder.setContentInfo("2");

        builder.setAutoCancel(true);

        builder.setNumber(2);

        builder.setSmallIcon(R.mipmap.ic_launcher);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground));

//        Intent intent = new Intent(this,SettingsActivity.class);
//
//        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
//
//        builder.setContentIntent(pIntent);

        //设置震动

        //long[] vibrate = {100,200,100,200};

        //builder.setVibrate(vibrate);



        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        Notification notification = builder.build();

        manger.notify(TYPE_Normal,notification);

    }


    private void bigTextStyle(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("BigTextStyle");
        builder.setContentText("BigTextStyle演示示例");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background));
        android.support.v4.app.NotificationCompat.BigTextStyle style = new android.support.v4.app.NotificationCompat.BigTextStyle();
        style.bigText("这里是点击通知后要显示的正文，可以换行可以显示很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长");
        style.setBigContentTitle("点击后的标题");
        style.setSummaryText("末尾只一行的文字内容");
        builder.setStyle(style);
        builder.setAutoCancel(true);
//        Intent intent = new Intent(this,SettingsActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
//        builder.setContentIntent(pIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manger.notify(2,notification);
    }

    public void inBoxStyle(){
        /**     * 最多显示五行 再多会有截断    */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("InboxStyle");
        builder.setContentText("InboxStyle演示示例");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background));
        android.support.v4.app.NotificationCompat.InboxStyle style = new android.support.v4.app.NotificationCompat.InboxStyle();
        style.setBigContentTitle("BigContentTitle")
                .addLine("第一行，第一行，第一行，第一行，第一行，第一行，第一行")
                .addLine("第二行")
                .addLine("第三行")
                .addLine("第四行")
                .addLine("第五行")
                .setSummaryText("SummaryText");
        builder.setStyle(style);
        builder.setAutoCancel(true);
//        Intent intent = new Intent(this,SettingsActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
//        builder.setContentIntent(pIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manger.notify(3,notification);
    }

    public void bigPictureStyle(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("BigPictureStyle");
        builder.setContentText("BigPicture演示示例");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background));
        android.support.v4.app.NotificationCompat.BigPictureStyle style = new android.support.v4.app.NotificationCompat.BigPictureStyle();
        style.setBigContentTitle("BigContentTitle");
        style.setSummaryText("SummaryText");
        style.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground));
        builder.setStyle(style);
        builder.setAutoCancel(true);
//        Intent intent = new Intent(this,ImageActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
//        builder.setContentIntent(pIntent);
        Notification notification = builder.build();
        manger.notify(4,notification);
    }

    private void hangup(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            Toast.makeText(MainActivity.this, "此类通知在Android 5.0以上版本才会有横幅有效！", Toast.LENGTH_SHORT).show();
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("横幅通知");
        builder.setContentText("请在设置通知管理中开启消息横幅提醒权限");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground));
//        Intent intent = new Intent(this,ImageActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
//        builder.setContentIntent(pIntent);
//        builder.setFullScreenIntent(pIntent,true);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        manger.notify(5,notification);
    }

    public void onDialogClick6(View v){

        Calendar calendar = Calendar.getInstance();// import java.util.Calendar

        calendar.setTimeInMillis(System.currentTimeMillis());

        int year = calendar.get(Calendar.YEAR);

        int monthOfyear = calendar.get(Calendar.MONTH);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        //通过DatePickerDialog来创建日期选择对话框

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                //当时间被设置后回调的方法

                Toast.makeText(MainActivity.this,

                        year + "年" + monthOfYear + "月" + dayOfMonth + "日",

                        Toast.LENGTH_SHORT).show();

            }

        }, year, monthOfyear, dayOfMonth );

        dpd.show();

    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(lisenter);
        registerForContextMenu(btn1);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_background);
        }
    }

    public void btnToast5(View V) {

        Toast toast = Toast.makeText(this, "可以设置时长的Toast", Toast.LENGTH_LONG);

        showMyToast(toast, 1 * 1000); //第一个参数：我们创建的Toast对象，第二个参数：我们想要设置显示的毫秒数

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void showMyToast(final Toast toast, final int cnt) {

        //注意: 此方法在 API 24 有效，在 API 26+ 失效。新方法还需进一步研究。2017.12.7 edit by David。

        // 该方法创建Toast对象的时候时长因该设置为 Toast.LENGTH_LONG,因为该他的时长就是3秒,与下面的延时时间对应

        //cnt:需要显示的时长,毫秒

        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override

            public void run() {

                toast.show();

            }

        }, 0, 3000);//每隔三秒调用一次show方法;

        new Timer().schedule(new TimerTask() {

            @Override

            public void run() {

                toast.cancel();

                timer.cancel();

            }

        }, cnt);//经过多长时间关闭该任务

    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if (v == btn1) {

            menu.add(0, 1, 0, "我是菜单1");

            menu.add(0, 2, 0, "我是菜单2");

            menu.add(0, 3, 0, "我是菜单3");

        }


        super.onCreateContextMenu(menu, v, menuInfo);

    }

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case 1:

                Toast.makeText(MainActivity.this, "菜单1", Toast.LENGTH_SHORT).show();

                break;

            case 2:

                Toast.makeText(MainActivity.this, "菜单2", Toast.LENGTH_SHORT).show();

                break;

            case 3:

                Toast.makeText(MainActivity.this, "菜单3", Toast.LENGTH_SHORT).show();

                break;
        }

        return true;

    }

    private View.OnClickListener lisenter = new View.OnClickListener() {//侦听器

        public void onClick(View view) {//点击事件
            Button button = (Button) view;
            if (button.getId() == R.id.button1) {
                //button.setText("Get it");
                //button.setBackgroundColor(1);
                //btnToast5(view);
                //onDialogClick6(view);
                //simpleNotify();第一个简单的通知
                //bigTextStyle();第二长长的通知。
                //inBoxStyle();通知
                //bigPictureStyle();带图片的通知
                //hangup();横屏通知
                Uri uri = Uri.parse("tel:10086");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        }
    };


}
