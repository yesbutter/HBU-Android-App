package com.example.a12968.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;


// Edit by HBU-Yesbutter 2017.12.9

// Upgrade to Android Studio 3.0.1 ,Gradle 4.1 ,Yesbutter 2017.12.9



public class MainActivity extends AppCompatActivity {

    //变量定义

    private EditText editText;          //输入框：用于输入数字

    private String operator;            //操作符：记录 + - * / 符号

    private boolean ends = false, opfirst = true;       //判断是否是计算结果

    private double n1 = 0, n2, Result;     //操作数：操作符两端的数字，n1为左操作数，n2为右操作数。

    private TextView textView;          //文本框：显示计算过程和计算结果

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;   //按钮：十个数字

    private Button btnPlus, btnMinus, btnMultiply, btnDivide;              //按钮：加减乘除

    private Button btnPoint, btnEqual, btnClear;                          //按钮：小数点，等号，清空


    private int activity_main;
    private View.OnClickListener lisenter = new View.OnClickListener() {//侦听器

        @Override

        public void onClick(View view) {//点击事件

            editText = findViewById(R.id.editviewdavid);//与XML中定义好的EditText控件绑定

            textView = findViewById(R.id.textviewdavid);//与XML中定义好的TextView控件绑定

            editText.setCursorVisible(false);//隐藏输入框光标

            String str;

            Button button = (Button) view;   //把点击获得的id信息传递给button

            DecimalFormat MyFormat = new DecimalFormat("###.####");//控制Double转为String的格式


            try {

                if (button.getId() == R.id.button0 || button.getId() == R.id.button1 || button.getId() == R.id.button2 || button.getId() == R.id.button3 || button.getId() == R.id.button4 || button.getId() == R.id.button5 || button.getId() == R.id.button6 || button.getId() == R.id.button7 || button.getId() == R.id.button8 || button.getId() == R.id.button9) {
                    if (ends == true) {     //判断是否是计算结果
                        editText.setText("");
                        opfirst = true;
                        ends = false;
                    }
                    editText.setText(editText.getText().toString() + button.getText().toString());


                }

                if (button.getId() == R.id.buttonClear) {
                    editText.setText("");

                    textView.setText("");

                    n1 = 0;
                    n2 = 0;
                    Result = 0;
                    operator = "";
                    opfirst = true;
                }

                if (button.getId() == R.id.buttonPoint) {
                    str = editText.getText().toString();

                    if (str.indexOf(".") != -1) //判断字符串中是否已包含小数点，如果有，就什么也不做

                    {
                    } else //如果没有小数点

                    {

                        if (str.equals("0"))//如果开始为0

                            editText.setText(("0" + ".").toString());

                        else if (str.equals(""))//如果初时显示为空，就什么也不做

                        {
                        } else

                            editText.setText(str + ".");

                    }
                }

                if (button.getId() == R.id.buttonPlus || button.getId() == R.id.buttonMinus || button.getId() == R.id.buttonMultiply || button.getId() == R.id.buttonDivide) {
                    if (editText.getText().toString().isEmpty())
                        opfirst = true;

                    if (opfirst) {
                        str = editText.getText().toString();
                        n1 = Double.parseDouble(str);
                        operator = button.getText().toString();
                        editText.setText("");
                        textView.setText(MyFormat.format(n1) + operator);
                        opfirst = false;
                    } else {
                        operator = button.getText().toString();
                        if (button.getId() == R.id.buttonPlus) {
                            n1 = n1 + Double.parseDouble(editText.getText().toString());
                            textView.setText(MyFormat.format(n1) + "" + operator);
                            editText.setText("");
                        } else if (button.getId() == R.id.buttonMinus) {
                            n1 = n1 - Double.parseDouble(editText.getText().toString());
                            textView.setText(MyFormat.format(n1) + "" + operator);
                            editText.setText("");
                        } else if (button.getId() == R.id.buttonMultiply) {
                            n1 = n1 * Double.parseDouble(editText.getText().toString());
                            textView.setText(MyFormat.format(n1) + "" + operator);
                            editText.setText("");
                        } else if (button.getId() == R.id.buttonDivide) {
                            if (Double.parseDouble(editText.getText().toString()) != 0) {
                                n1 = n1 + Double.parseDouble(editText.getText().toString());
                                textView.setText(MyFormat.format(n1) + "" + operator);
                                editText.setText("");
                            } else {
                                editText.setText("");
                                textView.setText("除数不能为0");
                            }
                        }

                    }
                }

                if (button.getId() == R.id.buttonEqual) {
                    ends = true;    //设置是输入等号
                    opfirst = true;
                    if (operator.equals("")) {
                        n1 = Double.parseDouble(editText.getText().toString());
                        textView.setText(MyFormat.format(n1));
                    }

                    if (operator.equals("+"))

                    {

                        n2 = Double.parseDouble(editText.getText().toString());

                        Result = n1 + n2;

                        textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "=" + MyFormat.format(Result));

                        editText.setText(MyFormat.format(Result) + "");

                        operator = "";

                    } else if (operator.equals("-"))

                    {

                        str = editText.getText().toString();

                        n2 = Double.parseDouble(str);

                        Result = n1 - n2;

                        textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "=" + MyFormat.format(Result));

                        editText.setText(MyFormat.format(Result) + "");
                        operator = "";

                    } else if (operator.equals("*"))

                    {

                        n2 = Double.parseDouble(editText.getText().toString());

                        Result = n1 * n2;

                        textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "=" + MyFormat.format(Result));

                        editText.setText(MyFormat.format(Result) + "");
                        operator = "";

                    } else if (operator.equals("/"))

                    {

                        str = editText.getText().toString();

                        n2 = Double.parseDouble(str);


                        if (n2 == 0)

                        {

                            editText.setText("");

                            textView.setText("除数不能为0");

                        } else

                        {

                            Result = n1 / n2;

                            textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "=" + MyFormat.format(Result));

                            editText.setText(MyFormat.format(Result) + "");

                        }
                        operator = "";
                    }
                }

            } catch (Exception e) {
            }

        }

    };


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //获取按钮的id

        btn1 = findViewById(R.id.button1);

        btn2 = findViewById(R.id.button2);

        btn3 = findViewById(R.id.button3);

        btn4 = findViewById(R.id.button4);

        btn5 = findViewById(R.id.button5);

        btn6 = findViewById(R.id.button6);

        btn7 = findViewById(R.id.button7);

        btn8 = findViewById(R.id.button8);

        btn9 = findViewById(R.id.button9);

        btn0 = findViewById(R.id.button0);

        btnPlus = findViewById(R.id.buttonPlus);

        btnMinus = findViewById(R.id.buttonMinus);

        btnMultiply = findViewById(R.id.buttonMultiply);

        btnDivide = findViewById(R.id.buttonDivide);

        btnPoint = findViewById(R.id.buttonPoint);

        btnEqual = findViewById(R.id.buttonEqual);

        btnClear = findViewById(R.id.buttonClear);

        //为按钮添加监听器

        btn1.setOnClickListener(lisenter);

        btn2.setOnClickListener(lisenter);

        btn3.setOnClickListener(lisenter);

        btn4.setOnClickListener(lisenter);

        btn5.setOnClickListener(lisenter);

        btn6.setOnClickListener(lisenter);

        btn7.setOnClickListener(lisenter);

        btn8.setOnClickListener(lisenter);

        btn9.setOnClickListener(lisenter);

        btn0.setOnClickListener(lisenter);

        btnPlus.setOnClickListener(lisenter);

        btnMinus.setOnClickListener(lisenter);

        btnMultiply.setOnClickListener(lisenter);

        btnDivide.setOnClickListener(lisenter);

        btnPoint.setOnClickListener(lisenter);

        btnEqual.setOnClickListener(lisenter);

        btnClear.setOnClickListener(lisenter);

    }

}