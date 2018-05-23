package com.example.qq1296821114.time_and_money.Presenter.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qq1296821114.time_and_money.Presenter.Activity.MainActivity;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.Util.DataBaseUtil;
import com.example.qq1296821114.time_and_money.Util.MyUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Register_Dialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "i ma a tag";
    private static final int ERROR=-1;
    private static final int FINISH=0;

    private Button register_register, register_login;
    private EditText register_email, register_password, register_user, register_repassword;
    private ConstraintLayout constraintLayout;
    private int color;
    private Context context=getContext();

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case ERROR:
                    register_user.setText("");
                    Toast.makeText(getContext(), "用户名重复", Toast.LENGTH_SHORT).show();
                    break;
                case FINISH:
                    Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                    dismiss();
                    break;
            }
        }
    };
    public Register_Dialog(@NonNull Context context, int color) {
        super(context);
        this.color = color;
    }

    @SuppressLint("RtlHardcoded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout._register_dialog);


        init_view();
        Window window = this.getWindow();

//重新设置
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = 100; // 新位置X坐标
        lp.y = 100; // 新位置Y坐标

// dialog.onWindowAttributesChanged(lp);
//(当Window的Attributes改变时系统会调用此函数)
        window.setAttributes(lp);

    }

    private void init_view() {
        register_user = findViewById(R.id.register_user);
        register_password = findViewById(R.id.register_password);
        register_repassword = findViewById(R.id.register_repassword);
        register_email = findViewById(R.id.register_email);
        register_register = findViewById(R.id.register_regeister);
        register_login = findViewById(R.id.register_back);
        constraintLayout = findViewById(R.id._register_constraintLayout);
        register_login.setOnClickListener(this);
        register_register.setOnClickListener(this);

        constraintLayout.setBackgroundColor(color);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_back:
                dismiss();
                break;
            case R.id.register_regeister:
                if (check() && MyUtil.isNetworkAvailable()) {
                    final String insert = "INSERT INTO person (person_name,person_password,person_theme) " +
                            "VALUES('" + register_user.getText().toString() + "','"
                            + register_password.getText().toString() + "'," + R.style.ZhiHuBlueTheme + ");";
                    DataBaseUtil.dataBase_register( insert, new DataBaseUtil.dataBase_register_Listener() {
                        @Override
                        public void finish(String result) {
                            Message message=new Message();
                            message.what=FINISH;
                            handler.sendMessage(message);
                        }
                        @Override
                        public void error() {
                            Message message=new Message();
                            message.what=ERROR;
                            handler.sendMessage(message);
                        }
                    });
                }
                break;
        }
    }


    private boolean check() {
        if (!register_password.getText().toString().equals(register_repassword.getText().toString())
                || register_password.getText().toString().length() < 6
                || register_password.getText().toString().length() > 18) {
            Log.e(TAG, "check: " + register_password.getText().toString());
            Toast.makeText(getContext(), "请保证密码输入合法", Toast.LENGTH_SHORT).show();
            register_password.setText("");
            register_repassword.setText("");
            return false;
        }
        if (register_user.getText().toString().length() > 18 || register_user.getText().toString().length() < 6) {
            Toast.makeText(getContext(), "请保证昵称合法", Toast.LENGTH_SHORT).show();
            register_user.setText("");
            return false;
        }
        return true;
    }
}
