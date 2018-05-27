package com.example.qq1296821114.time_and_money.Presenter.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.example.qq1296821114.time_and_money.R;

public class ChooseColorDialog extends Dialog implements View.OnClickListener{

    private ColorChoose colorChoose;

    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    public ChooseColorDialog(@NonNull Context context,ColorChoose colorChoose) {
        super(context,R.style.dialog);
        this.colorChoose=colorChoose;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._choose_color_dialog);
        btn1=findViewById(R.id.哔哩粉);
        btn2=findViewById(R.id.知乎蓝);
        btn3=findViewById(R.id.酷安绿);
        btn4=findViewById(R.id.网易红);
        btn5=findViewById(R.id.藤萝紫);
        btn6=findViewById(R.id.碧绿蓝);
        btn7=findViewById(R.id.樱草绿);
        btn8=findViewById(R.id.咖啡棕);
        btn9=findViewById(R.id.柠檬橙);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        colorChoose.colorChoose(view);
        dismiss();
    }

    public interface ColorChoose
    {
        void colorChoose(View view);
    }
}
