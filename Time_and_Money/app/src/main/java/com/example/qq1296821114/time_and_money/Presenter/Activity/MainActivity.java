package com.example.qq1296821114.time_and_money.Presenter.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.qq1296821114.time_and_money.Adapter.FragmentAdapter;
import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.Presenter.Dialog.ChooseColorDialog;
import com.example.qq1296821114.time_and_money.Presenter.Dialog.DataSync_Dialog;
import com.example.qq1296821114.time_and_money.Presenter.Dialog.Login_Dialog;
import com.example.qq1296821114.time_and_money.Presenter.Dialog.Register_Dialog;
import com.example.qq1296821114.time_and_money.Presenter.Fragment.Money_AddFragment;
import com.example.qq1296821114.time_and_money.Presenter.Fragment.Money_Fragment;
import com.example.qq1296821114.time_and_money.Presenter.Fragment.Time_AddFragment;
import com.example.qq1296821114.time_and_money.Presenter.Fragment.Time_Fragment;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.View.MyViewPage;

/**
 * 主活动，用来显示，和处理一些简单的逻辑。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, Money_Fragment.Money_add_Click, Money_AddFragment.Money_add_Over, Time_Fragment.Time_add_Click, Time_AddFragment.Time_add_Over, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final int REFRESH = -1;

    private MyViewPage viewPager = null;
    private FragmentAdapter myfragmentAdapter;
    private RelativeLayout _money_button, _my_button, _plan_button, _time_button;
    private TextView textView;
    private ImageView _main_menu, _main_mybutton;
    private static MyDB myDB;
    private static Context context;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Button _draw_login, _draw_register;
    private DrawerLayout drawerLayout;
    private static int colors = R.color.coffeeBrown;
    private ChooseColorDialog.ColorChoose colorChoose = new ChooseColorDialog.ColorChoose() {
        @SuppressLint("ApplySharedPref")
        @Override
        public void colorChoose(View view) {
            Intent intent = new Intent();
            intent.setClass(context, MainActivity.class);
            int id, color;
            switch (view.getId()) {
                case R.id.哔哩粉:
                    id = R.style.BiLiPinkTheme;
                    color = R.color.biliPink;
                    break;
                case R.id.知乎蓝:
                    id = R.style.ZhiHuBlueTheme;
                    color = R.color.zhihuBlue;
                    break;
                case R.id.酷安绿:
                    id = R.style.KuAnGreenTheme;
                    color = R.color.kuanGreen;
                    break;
                case R.id.网易红:
                    id = R.style.CloudRedTheme;
                    color = R.color.cloudRed;
                    break;
                case R.id.碧绿蓝:
                    id = R.style.SeaBlueTheme;
                    color = R.color.seaBlue;
                    break;
                case R.id.藤萝紫:
                    id = R.style.TengLuoPurpleTheme;
                    color = R.color.tengluoPurple;
                    break;
                case R.id.樱草绿:
                    id = R.style.GrassGreenTheme;
                    color = R.color.grassGreen;
                    break;
                case R.id.咖啡棕:
                    id = R.style.CoffeeBrownTheme;
                    color = R.color.coffeeBrown;
                    break;
                case R.id.柠檬橙:
                    id = R.style.LemonOrangeTheme;
                    color = R.color.lemonOrange;
                    break;
                default:
                    id = R.style.ZhiHuBlueTheme;
                    color = R.color.zhihuBlue;
                    break;
            }
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putInt("id", id);
            editor.putInt("color", color);
            editor.commit();

            Message message = new Message();
            message.what = REFRESH;
            handler.sendMessage(message);

        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ((msg.what)) {
                case REFRESH:
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
//                    onCreate(null);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = MyDB.getMyDB(this);
        context = this;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (sharedPreferences.getInt("id", 0) != 0) {
            setTheme(sharedPreferences.getInt("id", 0));
            setColor(getResources().getColor(sharedPreferences.getInt("color", 0)));
        } else {
            setTheme(R.style.ZhiHuBlueTheme);
            setColor(getResources().getColor(R.color.zhihuBlue));
        }
        setContentView(R.layout.activity_main);
        init_view();

        if (!sharedPreferences.getString("user", "").equals("")) {
            Log.e(TAG, "onCreate: " + sharedPreferences.getString("user", ""));
            _draw_register.setText("注销");
            _draw_login.setText("数据同步");
        }
    }

    //找到视图，设置监听时间
    @SuppressLint("CutPasteId")
    private void init_view() {
        viewPager = findViewById(R.id.main_viewpager);
        viewPager.setScanScroll(false);
        myfragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(myfragmentAdapter);
        _money_button = findViewById(R.id._money_button);
        _my_button = findViewById(R.id._my_button);
        _plan_button = findViewById(R.id._plan_button);
        _time_button = findViewById(R.id._time_button);
        toolbar = findViewById(R.id._main_Toolbar);
        _money_button.setOnClickListener(this);
        _my_button.setOnClickListener(this);
        _plan_button.setOnClickListener(this);
        _time_button.setOnClickListener(this);

        _main_menu = findViewById(R.id._main_menu);
        _main_mybutton = findViewById(R.id._main_mybutton);
        _main_menu.setOnClickListener(this);
        _main_mybutton.setOnClickListener(this);

        textView = findViewById(R.id._main_mytext);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        drawerLayout = findViewById(R.id._main_drawer_layout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Animation rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_anim);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setFillEnabled(true);
                _main_mybutton.startAnimation(rotateAnimation);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Animation rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.reoate_reanim);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setFillEnabled(true);
                _main_mybutton.startAnimation(rotateAnimation);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        _draw_login = navigationView.getHeaderView(0).findViewById(R.id._drawer_login);
        _draw_register = navigationView.getHeaderView(0).findViewById(R.id._drawer_register);
        _draw_login.setOnClickListener(this);
        _draw_register.setOnClickListener(this);
    }

    @Override
    public void finish() {
        super.finish();
        Log.e(TAG, "finish: ");
    }

    //点击事件
    @SuppressLint("CommitPrefEdits")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._money_button:
                viewPager.setCurrentItem(0);
                myfragmentAdapter.notifyDataSetChanged();
                textView.setText("Money");
                break;
            case R.id._my_button:
                viewPager.setCurrentItem(1);
                myfragmentAdapter.notifyDataSetChanged();
                textView.setText("My");
                break;
            case R.id._plan_button:
                viewPager.setCurrentItem(2);
                myfragmentAdapter.notifyDataSetChanged();
                break;
            case R.id._time_button:
                viewPager.setCurrentItem(3);
                textView.setText("Time");
                myfragmentAdapter.notifyDataSetChanged();
                break;

            case R.id._money_add:
                Log.e(TAG, "onClick: " + "click");
            case R.id._main_menu: {
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }
            case R.id._main_mybutton: {
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                } else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
            break;
            case R.id._drawer_login:
                if (_draw_login.getText().toString().equals("登录")) {
                    Login_Dialog login_dialog = new Login_Dialog(this, getColors(), new Login_Dialog.Login_dialog_listener() {
                        @Override
                        public void login() {
                            _draw_register.setText("注销");
                            _draw_login.setText("数据同步");
                        }
                    });
                    login_dialog.show();
                } else {
                    DataSync_Dialog dataSync_dialog = new DataSync_Dialog(this, getColors());
                    dataSync_dialog.show();
                }
                break;
            case R.id._drawer_register:
                if (_draw_register.getText().toString().equals("注册")) {
                    Register_Dialog registerDialog = new Register_Dialog(this, getColors());
                    registerDialog.show();
                } else {
                    _draw_register.setText("注册");
                    _draw_login.setText("登录");
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Log.e(TAG, "onClick: " + sharedPreferences.getString("user", ""));
                    editor.remove("user");
                    editor.remove("password");
                    editor.commit();
                    Log.e(TAG, "onClick: " + sharedPreferences.getString("user", ""));
                }
                break;
            default:
                break;
        }

    }

    public static void setColor(int color) {
        colors = color;
    }

    public static int getColors() {
        return colors;
    }

    public static void saveMoney(Money money) {
        myDB.saveMoney(money);
    }

    @Override
    public void OneBtnClick() {
        viewPager.setCurrentItem(5, false);
        myfragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEnd() {
        viewPager.setCurrentItem(0, false);
        myfragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void OneBtnClick2() {
        viewPager.setCurrentItem(6, false);
        myfragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void on_time_finish() {
        viewPager.setCurrentItem(3, false);
        myfragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_管理账单:
                viewPager.setCurrentItem(4, false);
                myfragmentAdapter.notifyDataSetChanged();
                break;
            case R.id.nav_更改颜色:
                ChooseColorDialog chooseColorDialog = new ChooseColorDialog(this, colorChoose);
                chooseColorDialog.show();
                break;
            case R.id.nav_提醒记帐:
                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivityForResult(intent, 0);
                break;
            case R.id.nav_退出:
                finish();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void changeColor(int color) {
//        toolbar = findViewById(R.id._main_Toolbar);
//        toolbar.setBackgroundColor(getResources().getColor(color));
//        _money_button.setBackgroundColor(getResources().getColor(color));
//        _my_button.setBackgroundColor(getResources().getColor(color));
//        _draw_register.setBackgroundColor(getResources().getColor(color));
//        _draw_login.setBackgroundColor(getResources().getColor(color));
//        setColor(getResources().getColor(color));
//    }
}
