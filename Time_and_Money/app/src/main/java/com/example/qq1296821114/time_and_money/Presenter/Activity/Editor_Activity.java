package com.example.qq1296821114.time_and_money.Presenter.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qq1296821114.time_and_money.Adapter.Money_AddAdapter;
import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Date;
import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.Util.MyUtil;

/**
 * Created by 12968 on 2018/4/13.
 */

public class Editor_Activity extends AppCompatActivity implements View.OnClickListener, Money_AddAdapter.MyOnItemClickListener {
    private Button btn1, btn0, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnd, btndel;
    private TextView textView, text_type, text_date;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private MyDB myDB;
    private Money money;
    private RelativeLayout relativeLayout;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout._money_add_layout);

        Bundle bundle = getIntent().getExtras();
        String[] strings=bundle.getString("date").split("-");
        money=new Money(bundle.getDouble("money"),bundle.getString("type"),bundle.getInt("imageid"),
                new Date(Integer.valueOf(strings[0]),Integer.valueOf(strings[1]),Integer.valueOf(strings[2]),
                        Integer.valueOf(strings[3]),Integer.valueOf(strings[4]),Integer.valueOf(strings[5])));
        money.setId(bundle.getInt("id"));
        myDB = MyDB.getMyDB(this);
        init_view();
    }

    private void init_view() {
        textView = findViewById(R.id._money_dialog_money);
        text_type = findViewById(R.id._money_dialog_type);
        imageView = findViewById(R.id._money_dialog_show);
        text_date = findViewById(R.id._money_add_date);
        text_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.showDatePickerDialog(activity, text_date);
            }
        });
        relativeLayout=findViewById(R.id._money_dialog_lin);
        relativeLayout.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id._money_dialog_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Money_AddAdapter money_addAdapter = new Money_AddAdapter(this);
        recyclerView.setAdapter(money_addAdapter);


        btn0 = findViewById(R.id._money_dialog_0);
        btn1 = findViewById(R.id._money_dialog_1);
        btn2 = findViewById(R.id._money_dialog_2);
        btn3 = findViewById(R.id._money_dialog_3);
        btn4 = findViewById(R.id._money_dialog_4);
        btn5 = findViewById(R.id._money_dialog_5);
        btn6 = findViewById(R.id._money_dialog_6);
        btn7 = findViewById(R.id._money_dialog_7);
        btn8 = findViewById(R.id._money_dialog_8);
        btn9 = findViewById(R.id._money_dialog_9);
        btndel = findViewById(R.id._money_dialog_del);
        btnd = findViewById(R.id._money_dialog_d);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btndel.setOnClickListener(this);
        btnd.setOnClickListener(this);

        btndel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!textView.getText().toString().isEmpty())
                    textView.setText("");
                return false;
            }
        });

        textView.setText(String.valueOf(money.getMoney())+"元");
        text_date.setText(money.getDate());
        text_type.setText(money.getType());
        imageView.setImageResource(money.getImageId());

    }

    @SuppressLint("SetTextI18n")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._money_dialog_0:
                textView.setText(textView.getText().toString() + "0");
                break;
            case R.id._money_dialog_1:
                textView.setText(textView.getText().toString() + "1");
                break;
            case R.id._money_dialog_2:
                textView.setText(textView.getText().toString() + "2");
                break;
            case R.id._money_dialog_3:
                textView.setText(textView.getText().toString() + "3");
                break;
            case R.id._money_dialog_4:
                textView.setText(textView.getText().toString() + "4");
                break;
            case R.id._money_dialog_5:
                textView.setText(textView.getText().toString() + "5");
                break;
            case R.id._money_dialog_6:
                textView.setText(textView.getText().toString() + "6");
                break;
            case R.id._money_dialog_7:
                textView.setText(textView.getText().toString() + "7");
                break;
            case R.id._money_dialog_8:
                textView.setText(textView.getText().toString() + "8");
                break;
            case R.id._money_dialog_9:
                textView.setText(textView.getText().toString() + "9");
                break;
            case R.id._money_dialog_d:
                if (!textView.getText().toString().contains(".")) {
                    textView.setText(textView.getText().toString() + ".");
                }
                break;
            case R.id._money_dialog_del:
                if (!textView.getText().toString().isEmpty()) {
                    String text = textView.getText().toString();
                    text = text.replace("元", "");
                    textView.setText(text.substring(0, text.length() - 1));
                }
                break;
        }

        String text = textView.getText().toString();
        if (!text.isEmpty())
            text = text.replace("元", "") + "元";
        textView.setText(text);
    }

    @Override
    public void onItemClick(int id, String name) {
        if (!textView.getText().toString().isEmpty()) {
            imageView.setImageResource(id);
            text_type.setText(name);

            Date date;
            String string = text_date.getText().toString();
            if (string.equals("今日")) {
                date = new Date();
            } else {
                String[] strings = string.split("-");
                date = new Date(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]), Integer.valueOf(strings[2]));
            }
            myDB.upDateMoney(money,new Money(Double.parseDouble(textView.getText().toString().replace("元", ""))
                    , name, id, date));

            Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "请先设置金额", Toast.LENGTH_SHORT).show();
        }
    }
}
