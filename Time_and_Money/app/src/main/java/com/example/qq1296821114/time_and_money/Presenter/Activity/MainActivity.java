package com.example.qq1296821114.time_and_money.Presenter.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.example.qq1296821114.time_and_money.Adapter.FragmentAdapter;
import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.Presenter.Dialog.ChangePhoto_Dialog;
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

import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主活动，用来显示，和处理一些简单的逻辑。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, Money_Fragment.Money_add_Click, Money_AddFragment.Money_add_Over, Time_Fragment.Time_add_Click, Time_AddFragment.Time_add_Over, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final int REFRESH = -1;
    private static final int PHOTO = 0;
    private static final int PHOTO_SD = 1;
    private static final int CROP_SMALL_PICTURE = 2;

    private Uri tempUri, uri_tempFile;
    private MyViewPage viewPager = null;
    private FragmentAdapter my_fragmentAdapter;
    private RelativeLayout _money_button, _my_button, _plan_button, _time_button,_admin_button;
    private TextView textView;
    private ImageView _main_menu, _main_my_button;
    private static MyDB myDB;
    private static Context context;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Button _draw_login, _draw_register;
    private DrawerLayout drawerLayout;
    private CircleImageView _draw_icon;
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
                case PHOTO:
                    Intent openCameraIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    tempUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "image.jpg"));
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    startActivityForResult(openCameraIntent, PHOTO);
                    break;
                case PHOTO_SD:
                    Intent openAlbumIntent = new Intent(
                            Intent.ACTION_GET_CONTENT);
                    openAlbumIntent.setType("image/*");
                    startActivityForResult(openAlbumIntent, PHOTO_SD);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO:
                    startPhotoZoom(tempUri);
                    break;
                case PHOTO_SD:
                    startPhotoZoom(data.getData());
                    break;
                case CROP_SMALL_PICTURE:
                    Log.e(TAG, "onActivityResult: " + 1);
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri_tempFile));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onActivityResult: " + 3);
                    }
                    _draw_icon.setImageBitmap(bitmap);
                    break;
            }
        }
    }

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
            if(sharedPreferences.getString("user", "").equals("admin"))
            {
                _admin_button.setVisibility(View.VISIBLE);
            }

        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        Log.e(TAG, "startPhotoZoom: " + "i am come");
        uri_tempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_tempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    //找到视图，设置监听时间
    @SuppressLint("CutPasteId")
    private void init_view() {
        viewPager = findViewById(R.id.main_viewpager);
        viewPager.setScanScroll(false);
        my_fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(my_fragmentAdapter);
        _money_button = findViewById(R.id._money_button);
        _my_button = findViewById(R.id._my_button);
        _plan_button = findViewById(R.id._plan_button);
        _time_button = findViewById(R.id._time_button);
        toolbar = findViewById(R.id._main_Toolbar);
        _admin_button=findViewById(R.id._admin_button);
        _money_button.setOnClickListener(this);
        _my_button.setOnClickListener(this);
        _plan_button.setOnClickListener(this);
        _time_button.setOnClickListener(this);
        _admin_button.setOnClickListener(this);

        _main_menu = findViewById(R.id._main_menu);
        _main_my_button = findViewById(R.id._main_mybutton);
        _main_menu.setOnClickListener(this);
        _main_my_button.setOnClickListener(this);

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
                _main_my_button.startAnimation(rotateAnimation);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Animation rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.reoate_reanim);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setFillEnabled(true);
                _main_my_button.startAnimation(rotateAnimation);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        _draw_login = navigationView.getHeaderView(0).findViewById(R.id._drawer_login);
        _draw_register = navigationView.getHeaderView(0).findViewById(R.id._drawer_register);
        _draw_icon = navigationView.getHeaderView(0).findViewById(R.id._drawer_icon);
        _draw_icon.setOnClickListener(this);
        _draw_login.setOnClickListener(this);
        _draw_register.setOnClickListener(this);
    }

    @Override
    public void finish() {
        super.finish();
        Log.e(TAG, "finish: ");
    }

    //点击事件
    @SuppressLint({"CommitPrefEdits", "SetTextI18n"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._money_button:
                viewPager.setCurrentItem(0);
                my_fragmentAdapter.notifyDataSetChanged();
                textView.setText("Money");
                break;
            case R.id._my_button:
                viewPager.setCurrentItem(1);
                my_fragmentAdapter.notifyDataSetChanged();
                textView.setText("My");
                break;
            case R.id._plan_button:
                viewPager.setCurrentItem(2);
                my_fragmentAdapter.notifyDataSetChanged();
                break;
            case R.id._time_button:
                viewPager.setCurrentItem(3);
                textView.setText("Time");
                my_fragmentAdapter.notifyDataSetChanged();
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
                    Login_Dialog login_dialog = new Login_Dialog(this, new Login_Dialog.Login_dialog_listener() {
                        @Override
                        public void login() {
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            if(sharedPreferences.getString("user","").equals("admin"))
                            {
                                _admin_button.setVisibility(View.VISIBLE);
                            }
                            _draw_register.setText("注销");
                            _draw_login.setText("数据同步");

                        }
                    });
                    login_dialog.show();
                } else {
                    DataSync_Dialog dataSync_dialog = new DataSync_Dialog(this, new DataSync_Dialog.DataSync_Dialog_Listener() {
                        @Override
                        public void refresh() {
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    dataSync_dialog.show();
                }
                break;
            case R.id._drawer_register:
                if (_draw_register.getText().toString().equals("注册")) {
                    Register_Dialog registerDialog = new Register_Dialog(this);
                    registerDialog.show();
                } else {
                    _draw_register.setText("注册");
                    _draw_login.setText("登录");
                    _admin_button.setVisibility(View.GONE);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Log.e(TAG, "onClick: " + sharedPreferences.getString("user", ""));
                    editor.remove("user");
                    editor.remove("password");
                    editor.commit();
                    Log.e(TAG, "onClick: " + sharedPreferences.getString("user", ""));
                }
                break;
            case R.id._drawer_icon:
                if (!_draw_login.getText().toString().equals("登录")) {
                    ChangePhoto_Dialog changePhoto_dialog = new ChangePhoto_Dialog(context, new ChangePhoto_Dialog.Changephoto_Listener() {
                        @Override
                        public void photo() {
                            requestCameraPermission();
                            requestContactsPermissions();
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                                    == PackageManager.PERMISSION_GRANTED) {
                                Message message = new Message();
                                message.what = PHOTO;
                                handler.sendMessage(message);
                            }
                        }

                        @Override
                        public void photo_sd() {
                            requestContactsPermissions();
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_GRANTED) {
                                Message message = new Message();
                                message.what = PHOTO_SD;
                                handler.sendMessage(message);
                            }
                        }
                    });
                    changePhoto_dialog.show();
                } else {
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id._admin_button:
                viewPager.setCurrentItem(7);
                my_fragmentAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void requestCameraPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this, "请先打开相机权限", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        0);
            }
        }
    }

    private void requestContactsPermissions() {
        Log.e(TAG, "requestContactsPermissions: "+"1" );
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // 如果是第二次申请，需要向用户说明为何使用此权限，会带出一个不再询问的复选框！
                Toast.makeText(this, "请先打开读写权限", Toast.LENGTH_SHORT).show();
            } else {
                // 第一次申请此权限，直接申请
                                Log.e(TAG, "requestContactsPermissions: "+"2" );
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                Log.e(TAG, "requestContactsPermissions: "+"2" );
            }
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
        my_fragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEnd() {
        viewPager.setCurrentItem(0, false);
        my_fragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void OneBtnClick2() {
        viewPager.setCurrentItem(6, false);
        my_fragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void on_time_finish() {
        viewPager.setCurrentItem(3, false);
        my_fragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_管理账单:
                viewPager.setCurrentItem(4, false);
                my_fragmentAdapter.notifyDataSetChanged();
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

}
