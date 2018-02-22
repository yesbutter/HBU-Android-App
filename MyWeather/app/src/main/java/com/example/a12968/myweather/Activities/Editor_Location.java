package com.example.a12968.myweather.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a12968.myweather.R;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *
 * Created by t-lidashao on 18-2-21.
 */

public class Editor_Location extends Activity {

    private ListView listView;
    private TextView addCity;
    private ArrayAdapter<String> arrayAdapter;

    private List<String> datalist = new ArrayList<String>();
    private Set<String> city = new HashSet<String>();

    private static final int QUERY_CITY = 0;


    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.editor_location);
        listView =findViewById(R.id.editor_list);
        addCity = findViewById(R.id.editor_addcity);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datalist);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String string = datalist.get(i);
                Log.e("---", "你选择的是" + string);
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("city_name",string);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Editor_Location.this);
                builder.setMessage("确认删除吗？");
                builder.setTitle("Tip");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        city.remove(datalist.get(position));
                        datalist.remove(position);
                        arrayAdapter.notifyDataSetChanged();
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
                    arrayAdapter.notifyDataSetChanged();
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
        BufferedReader bufferedReader ;
        String string;
        try {
            fileInputStream=openFileInput("city.txt");
            bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));
            while (true)
            {
                string=bufferedReader.readLine();
                if(string==null)
                {
                    break;
                }
                city.add(string);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null)
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
                arrayAdapter.notifyDataSetChanged();
                listView.setSelection(0);
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
        }
    }
}
