package com.example.qq1296821114.time_and_money.Presenter.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.qq1296821114.time_and_money.R;

public class DataSync_Dialog extends Dialog implements View.OnClickListener {
    private int color;
    private ConstraintLayout constraintLayout;
    private Button data_load, data_update;

    public DataSync_Dialog(@NonNull Context context, int color) {
        super(context);
        this.color = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout._data_sync_dialog);

        init_view();
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = 100; // 新位置X坐标
        lp.y = 100; // 新位置Y坐标
        window.setAttributes(lp);
    }

    private void init_view() {
        constraintLayout = findViewById(R.id._data_constraintLayout);
        data_load = findViewById(R.id._data_sync_load);
        data_update=findViewById(R.id._data_sync_update);
        data_load.setOnClickListener(this);
        data_update.setOnClickListener(this);

        constraintLayout.setBackgroundColor(color);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id._data_sync_load:
                break;
            case R.id._data_sync_update:
                break;
                default:
                    break;
        }
    }
}
