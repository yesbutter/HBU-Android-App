package com.example.qq1296821114.time_and_money.Presenter.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qq1296821114.time_and_money.Adapter.Money_AddAdapter;
import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Date;
import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.Util.MyUtil;

/**
 * Created by 12968 on 2018/4/7.
 */

public class Money_AddFragment extends Fragment implements View.OnClickListener, Money_AddAdapter.MyOnItemClickListener {
    private Button btn1, btn0, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnd, btndel;
    private TextView textView, text_type, text_date;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private MyDB myDB;

    public Money_AddFragment() {
        myDB = MyDB.getMyDB(getContext());
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._money_add_layout, container, false);
        init_view(view);

        return view;
    }


    private void init_view(View view) {
        textView = view.findViewById(R.id._money_dialog_money);
        text_type = view.findViewById(R.id._money_dialog_type);
        imageView = view.findViewById(R.id._money_dialog_show);
        text_date = view.findViewById(R.id._money_add_date);
        text_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.showDatePickerDialog(getActivity(), text_date);
            }
        });

        recyclerView = view.findViewById(R.id._money_dialog_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Money_AddAdapter money_addAdapter = new Money_AddAdapter(this);
        recyclerView.setAdapter(money_addAdapter);


        btn0 = view.findViewById(R.id._money_dialog_0);
        btn1 = view.findViewById(R.id._money_dialog_1);
        btn2 = view.findViewById(R.id._money_dialog_2);
        btn3 = view.findViewById(R.id._money_dialog_3);
        btn4 = view.findViewById(R.id._money_dialog_4);
        btn5 = view.findViewById(R.id._money_dialog_5);
        btn6 = view.findViewById(R.id._money_dialog_6);
        btn7 = view.findViewById(R.id._money_dialog_7);
        btn8 = view.findViewById(R.id._money_dialog_8);
        btn9 = view.findViewById(R.id._money_dialog_9);
        btndel = view.findViewById(R.id._money_dialog_del);
        btnd = view.findViewById(R.id._money_dialog_d);

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
    }

    @SuppressLint("SetTextI18n")
    @Override
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
            myDB.saveMoney(new Money(Double.parseDouble(textView.getText().toString().replace("元", ""))
                    , name, id, date));

            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();

            if (getActivity() instanceof Money_add_Over) {
                ((Money_add_Over) getActivity()).onEnd();
            }
        } else {
            Toast.makeText(getContext(), "请先设置金额", Toast.LENGTH_SHORT).show();
        }
    }

    public interface Money_add_Over {
        void onEnd();
    }
}
