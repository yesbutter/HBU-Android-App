package com.example.qq1296821114.time_and_money.Presenter.Dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.qq1296821114.time_and_money.Presenter.Activity.MainActivity;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.Util.DataBaseUtil;
import com.example.qq1296821114.time_and_money.Util.MyUtil;
import com.github.mikephil.charting.utils.Utils;

public class DataSync_Dialog extends Dialog implements View.OnClickListener {
    private ConstraintLayout constraintLayout;
    private Button data_download, data_upload;
    private MyDB myDB;
    private DataSync_Dialog_Listener dataSync_dialog_listener;

    public DataSync_Dialog(@NonNull Context context, DataSync_Dialog_Listener dataSync_dialog_listener) {
        super(context, R.style.dialog);
        this.dataSync_dialog_listener = dataSync_dialog_listener;
    }

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
        data_download.getBackground().setAlpha(200);
        data_upload.getBackground().setAlpha(200);
    }

    @Override
    public void onClick(View view) {
        String user;
        switch (view.getId()) {
            case R.id._data_sync_download:
                user = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("user", "");
                MyUtil.showProgressDialog(getContext());
                if (MyUtil.isNetworkAvailable(getContext())) {
                    DataBaseUtil.dataBase_download(myDB, user, new DataBaseUtil.DataBase_register_Listener() {
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
                    DataBaseUtil.dataBase_upload(myDB, user, new DataBaseUtil.DataBase_register_Listener() {
                        @Override
                        public void finish(String result) {
                            Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                            MyUtil.closeProgressDialog();
                        }

                        @Override
                        public void error() {
                            Toast.makeText(getContext(), "上传失败，请检查下网络", Toast.LENGTH_SHORT).show();
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
