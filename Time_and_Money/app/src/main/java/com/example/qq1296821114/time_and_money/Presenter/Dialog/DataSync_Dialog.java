package com.example.qq1296821114.time_and_money.Presenter.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.Util.DataBaseUtil;
import com.example.qq1296821114.time_and_money.Util.MyUtil;

public class DataSync_Dialog extends Dialog implements View.OnClickListener {
    private ConstraintLayout constraintLayout;
    private Button data_download, data_upload;
    private MyDB myDB;
    private DataSync_Dialog_Listener dataSync_dialog_listener;

    public DataSync_Dialog(@NonNull Context context, DataSync_Dialog_Listener dataSync_dialog_listener) {
        super(context, R.style.dialog);
        this.dataSync_dialog_listener = dataSync_dialog_listener;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    Toast.makeText(getContext(),"上传成功",Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(getContext(),"请检查网络，上传失败",Toast.LENGTH_SHORT).show();
                    break;
                    default:
                        break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout._data_sync_dialog);
        myDB = MyDB.getMyDB(getContext());

        init_view();
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = 100; // 新位置X坐标
        lp.y = 100; // 新位置Y坐标
        window.setAttributes(lp);

        window.setWindowAnimations(R.style.dialog_anim);
    }

    private void init_view() {
        constraintLayout = findViewById(R.id._data_constraintLayout);
        data_download = findViewById(R.id._data_sync_download);
        data_upload = findViewById(R.id._data_sync_upload);
        data_download.setOnClickListener(this);
        data_upload.setOnClickListener(this);

        constraintLayout.getBackground().setAlpha(200);
        data_download.getBackground().setAlpha(0);
        data_upload.getBackground().setAlpha(0);
    }

    @Override
    public void onClick(View view) {
        String user;
        switch (view.getId()) {
            case R.id._data_sync_download:
                user = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("user", "");
                MyUtil.showProgressDialog(getContext());
                if (MyUtil.isNetworkAvailable(getContext())) {
                    DataBaseUtil.dataBase_download(myDB, user, new DataBaseUtil.DataBase_Listener() {
                        @Override
                        public void finish(String result) {
                            dataSync_dialog_listener.refresh();
                            MyUtil.closeProgressDialog();
                        }

                        @Override
                        public void error() {
                            MyUtil.closeProgressDialog();
                        }
                    });
                }
                break;
            case R.id._data_sync_upload:
                user = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("user", "");
                MyUtil.showProgressDialog(getContext());
                if (MyUtil.isNetworkAvailable(getContext())) {
                    DataBaseUtil.dataBase_upload(myDB, user, new DataBaseUtil.DataBase_Listener() {
                        @Override
                        public void finish(String result) {
                            Message message=new Message();
                            message.what=1;
                            handler.sendMessage(message);
                            MyUtil.closeProgressDialog();
                        }

                        @Override
                        public void error() {
                            Message message=new Message();
                            message.what=0;
                            handler.sendMessage(message);
                            MyUtil.closeProgressDialog();
                        }
                    });
                }
                dismiss();
                break;
            default:
                break;
        }
    }


    public interface DataSync_Dialog_Listener {
        void refresh();
    }
}
