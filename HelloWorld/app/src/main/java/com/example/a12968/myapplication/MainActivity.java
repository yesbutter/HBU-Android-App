package com.example.a12968.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.text.DecimalFormat;


// Edit by HBU-Yesbutter 2017.12.9

// Upgrade to Android Studio 3.0.1 ,Gradle 4.1 ,Yesbutter 2017.12.9


public class MainActivity extends AppCompatActivity {

    //变量定义
    public android.support.v7.widget.Toolbar toolbar;             //顶部工具条
    public DrawerLayout mDrawerLayout;  //左侧抽屉
    public static final int TYPE_Normal = 1;
    private NotificationManager manger;
    private EditText editText;          //输入框：用于输入数字
    private String operator;            //操作符：记录 + - * / 符号
    private boolean ends = false, opfirst = true;       //判断是否是计算结果
    private double n1 = 0, n2, Result;     //操作数：操作符两端的数字，n1为左操作数，n2为右操作数。
    private TextView textView;          //文本框：显示计算过程和计算结果
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;   //按钮：十个数字
    private Button btnPlus, btnMinus, btnMultiply, btnDivide;              //按钮：加减乘除
    private Button btnPoint, btnEqual, btnClear, btnx, btnup5, btnup2;                          //按钮：小数点，等号，清空

    private void simpleNotify() {
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
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground));
//        Intent intent = new Intent(this,SettingsActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
//        builder.setContentIntent(pIntent);
        //设置震动
        //long[] vibrate = {100,200,100,200};
        //builder.setVibrate(vibrate);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manger.notify(TYPE_Normal, notification);

    }

    private void find_common_button() {
        editText = findViewById(R.id.editviewdavid);//与XML中定义好的EditText控件绑定
        textView = findViewById(R.id.textviewdavid);//与XML中定义好的TextView控件绑定
        editText.setCursorVisible(false);//隐藏输入框光标
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btn0 = (Button) findViewById(R.id.button0);
        btnPlus = (Button) findViewById(R.id.buttonPlus);
        btnMinus = (Button) findViewById(R.id.buttonMinus);
        btnMultiply = (Button) findViewById(R.id.buttonMultiply);
        btnDivide = (Button) findViewById(R.id.buttonDivide);
        btnPoint = (Button) findViewById(R.id.buttonPoint);
        btnEqual = (Button) findViewById(R.id.buttonEqual);
        btnClear = (Button) findViewById(R.id.buttonClear);
        btnx = (Button) findViewById(R.id.buttonX);
        //为按钮添加监听器
        btn1.setOnClickListener(lisenter);
        btn2.setOnClickListener(lisenter);
        btn3.setOnClickListener(lisenter);
        btn4.setOnClickListener(lisenter);
        btn5.setOnClickListener(lisenter);
        btn6.setOnClickListener(lisenter);
        btn7.setOnClickListener(lisenter);
        btn8.setOnClickListener(lisenter);
        btn9.setOnClickListener(lisenter);
        btn0.setOnClickListener(lisenter);
        btnPlus.setOnClickListener(lisenter);
        btnMinus.setOnClickListener(lisenter);
        btnMultiply.setOnClickListener(lisenter);
        btnDivide.setOnClickListener(lisenter);
        btnPoint.setOnClickListener(lisenter);
        btnEqual.setOnClickListener(lisenter);
        btnClear.setOnClickListener(lisenter);
        btnx.setOnClickListener(lisenter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnup2 = (Button) findViewById(R.id.buttonup2);
        btnup2.setOnClickListener(lisenter);
        btnup5 = (Button) findViewById(R.id.buttonup5);
        btnup5.setOnClickListener(lisenter);
        find_common_button();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {//抽屉菜单监听 2017.12.27
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, MyWebView.class);
                Uri uri;
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        uri = Uri.parse("tel:03125073279");
                        intent = new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(intent);
                        break;
                    case R.id.nav_index:
                        intent.putExtra("WebAddress", "http://cs.hbu.cn/index.aspx");
                        startActivity(intent);
                        break;
                    case R.id.nav_introduce:
                        intent.putExtra("WebAddress", "http://cs.hbu.cn/list.aspx?type=notice");
                        startActivity(intent);
                        break;
                    case R.id.nav_news:
                        intent.putExtra("WebAddress", "http://cs.hbu.cn/list.aspx?type=news");
                        startActivity(intent);
                        break;
                    case R.id.nav_university:
                        uri = Uri.parse("http://www.hbu.cn");
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        if(item.getItemId()==R.id.stand_cal)
        {
            setContentView(R.layout.activity_main);
            btnup2 = (Button) findViewById(R.id.buttonup2);
            btnup5 = (Button) findViewById(R.id.buttonup5);
            find_common_button();
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground);
            }
        }
        if(item.getItemId()==R.id.sci_cal)
        {
            setContentView(R.layout.scientific_cal_layout);
            find_common_button();
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground);
        }
        if(item.getItemId()==R.id.editold)
        {

        }
        if(item.getItemId()==R.id.settings)
        {

        }
        return true;
    }
    private View.OnClickListener lisenter = new View.OnClickListener() {//侦听器
        @Override
        public void onClick(View view) {//点击事件
            LinearLayout linearLayout = findViewById(R.id.Linear);
            String str;
            Button button = (Button) view;   //把点击获得的id信息传递给button
            DecimalFormat MyFormat = new DecimalFormat("###.####");//控制Double转为String的格式
            try {
                if (button.getId() == R.id.button0 || button.getId() == R.id.button1 || button.getId() == R.id.button2 || button.getId() == R.id.button3 || button.getId() == R.id.button4 || button.getId() == R.id.button5 || button.getId() == R.id.button6 || button.getId() == R.id.button7 || button.getId() == R.id.button8 || button.getId() == R.id.button9) {
                    if (ends == true) {     //判断是否是计算结果
                        editText.setText("");
                        opfirst = true;
                        ends = false;
                    }
                    editText.setText(editText.getText().toString() + button.getText().toString());
//                    Resources resources = getApplicationContext().getResources();
//                    Drawable drawable = resources.getDrawable(R.drawable.timg);
//                    linearLayout.setBackgroundDrawable(drawable);
                }
                if (button.getId() == R.id.buttonClear) {
                    editText.setText("");
                    textView.setText("");

                    n1 = 0;
                    n2 = 0;
                    Result = 0;
                    operator = "";
                    opfirst = true;
                }
                if (button.getId() == R.id.buttonup5) {
                    if (!editText.getText().toString().isEmpty()) {
                        double i = 0.01 * Double.parseDouble(editText.getText().toString());
                        editText.setText(Double.toString(i));
                    }
                }
                if (button.getId() == R.id.buttonup2) {
                    if (!editText.getText().toString().isEmpty()) {
                        double i = Double.parseDouble(editText.getText().toString());
                        editText.setText(Double.toString(i * i));
                    }
                }
                if (button.getId() == R.id.buttonX) {
                    String text = editText.getText().toString();
                    if (text.length() != 0) {
                        editText.setText(text.toString().substring(0, text.length() - 1));
                    }
                }
                if (button.getId() == R.id.buttonPoint) {
                    str = editText.getText().toString();
                    if (str.indexOf(".") != -1) ;//判断字符串中是否已包含小数点，如果有，就什么也不做
                    else //如果没有小数点
                    {
                        if (str.equals("0"))//如果开始为0
                            editText.setText(("0" + ".").toString());
                        else if (str.equals("")) ;//如果初时显示为空，就什么也不做
                        else
                            editText.setText(str + ".");
                    }
                }
                if (button.getId() == R.id.buttonPlus || button.getId() == R.id.buttonMinus || button.getId() == R.id.buttonMultiply || button.getId() == R.id.buttonDivide) {
//                    if(view.equals(R.layout.activity_main))
//                    {
//                        Resources resources = getApplicationContext().getResources();
//                        Drawable drawable = resources.getDrawable(R.drawable.a2);
//                        linearLayout.setBackgroundDrawable(drawable);
//                    }
                    if (editText.getText().toString().isEmpty())
                        opfirst = true;
                    if (opfirst) {
                        str = editText.getText().toString();
                        n1 = Double.parseDouble(str);
                        operator = button.getText().toString();
                        editText.setText("");
                        textView.setText(MyFormat.format(n1) + operator);
                        opfirst = false;
                    } else {
                        operator = button.getText().toString();
                        if (button.getId() == R.id.buttonPlus) {
                            n1 = n1 + Double.parseDouble(editText.getText().toString());
                            textView.setText(MyFormat.format(n1) + "" + operator);
                            editText.setText("");
                        } else if (button.getId() == R.id.buttonMinus) {
                            n1 = n1 - Double.parseDouble(editText.getText().toString());
                            textView.setText(MyFormat.format(n1) + "" + operator);
                            editText.setText("");
                        } else if (button.getId() == R.id.buttonMultiply) {
                            n1 = n1 * Double.parseDouble(editText.getText().toString());
                            textView.setText(MyFormat.format(n1) + "" + operator);
                            editText.setText("");
                        } else if (button.getId() == R.id.buttonDivide) {
                            if (Double.parseDouble(editText.getText().toString()) != 0) {
                                n1 = n1 + Double.parseDouble(editText.getText().toString());
                                textView.setText(MyFormat.format(n1) + "" + operator);
                                editText.setText("");
                            } else {
                                editText.setText("");
                                textView.setText("除数不能为0");
                            }
                        }
                    }
                }
                if (button.getId() == R.id.buttonEqual) {
                    simpleNotify();
//                    if(view.equals(R.layout.activity_main)) {
//                        Resources resources = getApplicationContext().getResources();
//                        Drawable drawable = resources.getDrawable(R.drawable.q123);
//                        linearLayout.setBackgroundDrawable(drawable);
//                    }
                    ends = true;    //设置是输入等号
                    opfirst = true;
                    if (operator.equals("")) {
                        n1 = Double.parseDouble(editText.getText().toString());
                        textView.setText(MyFormat.format(n1));
                    }
                    if (operator.equals("+")) {
                        n2 = Double.parseDouble(editText.getText().toString());
                        Result = n1 + n2;
                        textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "=" + MyFormat.format(Result));
                        editText.setText(MyFormat.format(Result) + "");
                        operator = "";
                    } else if (operator.equals("-")) {
                        str = editText.getText().toString();
                        n2 = Double.parseDouble(str);
                        Result = n1 - n2;
                        textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "=" + MyFormat.format(Result));
                        editText.setText(MyFormat.format(Result) + "");
                        operator = "";
                    } else if (operator.equals("*")) {
                        n2 = Double.parseDouble(editText.getText().toString());
                        Result = n1 * n2;
                        textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "=" + MyFormat.format(Result));
                        editText.setText(MyFormat.format(Result) + "");
                        operator = "";
                    } else if (operator.equals("/")) {
                        str = editText.getText().toString();
                        n2 = Double.parseDouble(str);
                        if (n2 == 0) {
                            editText.setText("");
                            textView.setText("除数不能为0");
                        } else {
                            Result = n1 / n2;
                            textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "=" + MyFormat.format(Result));
                            editText.setText(MyFormat.format(Result) + "");
                        }
                        operator = "";
                    }

                }

            } catch (Exception e) {
            }
        }
    };
}

