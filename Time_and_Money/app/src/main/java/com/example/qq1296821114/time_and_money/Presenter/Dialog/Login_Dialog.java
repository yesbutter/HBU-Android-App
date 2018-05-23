package com.example.qq1296821114.time_and_money.Presenter.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.Util.DataBaseUtil;

import static android.content.ContentValues.TAG;

public class Login_Dialog extends Dialog implements View.OnClickListener {
    private static final int ERROR = -1;
    private static final int YES = 1;
    private static final int NO = 0;
    private static final int NOTSURE = 2;
    private int color;
    private EditText login_user, login_password;
    private Button login_back, login_login;
    private ConstraintLayout constraintLayout;
    private Login_dialog_listener login_dialog_listener;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ERROR:
                    break;
                case YES:
                    Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                    editor.putString("user", login_user.getText().toString());
                    editor.putString("password", login_password.getText().toString());
                    editor.apply();
                    editor.commit();
                    login_dialog_listener.login();
                    dismiss();
                    break;
                case NOTSURE:
                    login_user.setText("");
                    Toast.makeText(getContext(), "请确认用户名是否正确", Toast.LENGTH_SHORT).show();
                    break;
                case NO:
                    login_password.setText("");
                    Toast.makeText(getContext(), "请确认密码是否正确", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public Login_Dialog(@NonNull Context context, int color,Login_dialog_listener login_dialog_listener) {
        super(context);
        this.color = color;
        this.login_dialog_listener=login_dialog_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout._login_dialog);

        init_view();

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = 100; // 新位置X坐标
        lp.y = 100; // 新位置Y坐标
        window.setAttributes(lp);

    }

    private void init_view() {
        constraintLayout = findViewById(R.id._login_constraintLayout);
        login_password = findViewById(R.id._login_password);
        login_user = findViewById(R.id._login_user);

        login_back = findViewById(R.id._login_back);
        login_login = findViewById(R.id._login_login);
        login_login.setOnClickListener(this);
        login_back.setOnClickListener(this);

        constraintLayout.setBackgroundColor(color);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._login_login:
                if (!login_user.getText().toString().isEmpty() && !login_password.getText().toString().isEmpty()) {
                    final String ans = login_password.getText().toString();
                    DataBaseUtil.dataBase_Check(login_user.getText().toString(), new DataBaseUtil.dataBase_register_Listener() {

                        @Override
                        public void finish(String result) {
                            Message message = new Message();
                            Log.e(TAG, "finish:"+result+" "+ans );
                            if (result!=null&&result.equals(ans)) {
                                message.what = YES;
                            } else {
                                if (result==null) {
                                    message.what = NOTSURE;
                                } else {
                                    message.what = NO;
                                }
                            }
                            handler.sendMessage(message);
                        }

                        @Override
                        public void error() {
                            Message message = new Message();
                            message.what = ERROR;
                            handler.sendMessage(message);
                        }
                    });
                }
                break;
            case R.id._login_back:
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface Login_dialog_listener {
        void login();
    }
}
