package com.example.a12968.myweather.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a12968.myweather.Adapter.MyeditorAdapter;
import com.example.a12968.myweather.R;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by t-lidashao on 18-2-21.
 */

public class Editor_Location extends Activity {

    private ListView listView;
    private TextView addCity;
    private MyeditorAdapter myeditorAdapter;

    private ArrayList<String> datalist = new ArrayList<String>();
    private Set<String> city = new HashSet<>();

    private static final int QUERY_CITY = 0;


    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


        setContentView(R.layout.editor_location);
        listView = findViewById(R.id.editor_list);
        addCity = findViewById(R.id.editor_addcity);


        /**
         * 继承ArrayAdapter的抽象adapter，实现了其中的conver，为事件的点击按钮添加Listener
         */
        myeditorAdapter = new MyeditorAdapter(this, R.layout.mylistview, datalist) {
            @Override
            public void conver(ViewHolder viewHolder, final int position) {

                viewHolder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SwipeMenuLayout.getViewCache().smoothClose();
                        city.remove(datalist.get(position));
                        datalist.remove(position);
                        myeditorAdapter.notifyDataSetChanged();
                    }
                });

                viewHolder.setOnClickListener(R.id.editor_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String string = datalist.get(position);
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("city_name", string);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

                viewHolder.setOnClickListener(R.id.btnTop, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("---", "你选择的是置顶");
                        String string=datalist.get(position);
                        datalist.add(0,string);
                        datalist.remove(position+1);
                        SwipeMenuLayout.getViewCache().smoothClose();
                        myeditorAdapter.notifyDataSetChanged();

                    }
                });
            }

        };


        listView.setAdapter(myeditorAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Editor_Location.this);
                builder.setMessage("确认删除吗？");
                builder.setTitle("Tip");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        city.remove(datalist.get(position));
                        datalist.remove(position);
                        myeditorAdapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
                return false;
            }
        });

        initloading();

    }


    public void editor_add_city(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Choose_City.class);
        startActivityForResult(intent, QUERY_CITY);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case QUERY_CITY:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String string = bundle.getString("city_name");
                    city.add(string);
                    datalist.add(string);
                    myeditorAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        save();
    }

    private void save() {
        BufferedWriter bufferedWriter = null;
        try {
            FileOutputStream fileOutputStream = openFileOutput("city.txt", Context.MODE_PRIVATE);

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            for (String string : city) {
                bufferedWriter.write(string + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initloading() {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader;
        String string;
        try {
            fileInputStream = openFileInput("city.txt");
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while (true) {
                string = bufferedReader.readLine();
                if (string == null) {
                    break;
                }
                city.add(string);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }


        if (city.size() > 0) {
            datalist.clear();
            for (String s : city) {
                datalist.add(s);
                myeditorAdapter.notifyDataSetChanged();
                listView.setSelection(0);
            }
            myeditorAdapter.notifyDataSetChanged();
            listView.setSelection(0);
        }
    }
}
