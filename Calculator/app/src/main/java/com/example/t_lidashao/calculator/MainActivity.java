package com.example.t_lidashao.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button btn1;

    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(lisenter);
        registerForContextMenu(btn1);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    public void btnToast1(View v) {

        Toast.makeText(getApplicationContext(), "Toast默认样式", Toast.LENGTH_LONG).show();//Toast.LENGTH_LONG（3.5秒.
    }

    public void btnToast2(View v) {

        Toast.makeText(this, "Toast默认样式", Toast.LENGTH_SHORT).show();        //Toast.LENGTH_SHORT（2秒）的值

    }

    public void btnToast5(View V) {

        Toast toast = Toast.makeText(this, "可以设置时长的Toast", Toast.LENGTH_LONG);

        showMyToast(toast, 1 * 1000); //第一个参数：我们创建的Toast对象，第二个参数：我们想要设置显示的毫秒数

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

    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 1, 0, "信息安全");
        menu.add(0, 2, 0, "网络工程");
        menu.add(0, 3, 0, "计算机科学与技术");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case 1:

                Toast.makeText(MainActivity.this, "我是计算机学院", Toast.LENGTH_SHORT).show();

                break;

            case 2:

                Toast.makeText(MainActivity.this, "我是电信学院", Toast.LENGTH_SHORT).show();

                break;

            case 3:

                Toast.makeText(MainActivity.this, "我是。。。", Toast.LENGTH_SHORT).show();

                break;

            case 4:

                Toast.makeText(MainActivity.this, "I am 。。。", Toast.LENGTH_SHORT).show();

                break;

        }

        return true;

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
                button.setText("Get it");
                button.setBackgroundColor(1);
                btnToast5(view);

            }
        }
    };


}
