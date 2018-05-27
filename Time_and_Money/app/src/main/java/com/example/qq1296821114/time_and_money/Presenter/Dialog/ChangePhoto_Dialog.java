package com.example.qq1296821114.time_and_money.Presenter.Dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.qq1296821114.time_and_money.Presenter.Activity.MainActivity;
import com.example.qq1296821114.time_and_money.R;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class ChangePhoto_Dialog extends Dialog implements View.OnClickListener {

    private ConstraintLayout constraintLayout;
    private Button photo, sdphoto;
    private Changephoto_Listener changephoto_listener;

    public ChangePhoto_Dialog(@NonNull Context context,Changephoto_Listener changephoto_listener) {
        super(context,R.style.dialog);
        this.changephoto_listener=changephoto_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout._choose_photo_dialog);
        init_view();


        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.dialog_bottom_anim);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    private void init_view() {
        constraintLayout = findViewById(R.id._choose_photo_const);
        photo = findViewById(R.id._choose_photo_photo);
        sdphoto = findViewById(R.id._choose_photo_sd);
        photo.setOnClickListener(this);
        sdphoto.setOnClickListener(this);

        constraintLayout.getBackground().setAlpha(200);
        photo.getBackground().setAlpha(0);
        sdphoto.getBackground().setAlpha(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._choose_photo_photo:
                changephoto_listener.photo();
                break;
            case R.id._choose_photo_sd:
                changephoto_listener.photo_sd();
                break;
            default:
                break;
        }
    }


    public interface Changephoto_Listener {
        void photo();
        void photo_sd();
    }
}
