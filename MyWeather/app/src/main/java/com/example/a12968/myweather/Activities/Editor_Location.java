package com.example.a12968.myweather.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a12968.myweather.Adapter.MyeditorAdapter;
import com.example.a12968.myweather.Bean.CityFragment;
import com.example.a12968.myweather.Bean.StringItem;
import com.example.a12968.myweather.DataBase.WeatherDB;
import com.example.a12968.myweather.Main.MainActivity;
import com.example.a12968.myweather.R;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * Created by t-lidashao on 18-2-21.
 */

public class Editor_Location extends Activity {

    private ListView listView;
    private MyeditorAdapter myeditorAdapter;

    private ArrayList<StringItem> datalist = new ArrayList<>();

    private static final int QUERY_CITY = 0;

    private WeatherDB weatherDB;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


        setContentView(R.layout.editor_location);
        listView = findViewById(R.id.editor_list);

        /*
          继承ArrayAdapter的抽象adapter，实现了其中的conver，为事件的点击按钮添加Listener
         */
        myeditorAdapter = new MyeditorAdapter(this, R.layout.mylistview, datalist) {
            @Override
            public void conver(final ViewHolder viewHolder, final int position) {

                viewHolder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        MainActivity.removeCityFragment(position);

                        SwipeMenuLayout.getViewCache().smoothClose();

                        CityFragment.delStringItem(datalist.get(position));
                        weatherDB.DelStringItem(datalist.get(position));
                        datalist.remove(position);
                        myeditorAdapter.notifyDataSetChanged();

                    }
                });

                viewHolder.setOnClickListener(R.id.editor_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        String string = datalist.get(position).getString();
//                        Intent intent = new Intent();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("city_name", string);
//                        intent.putExtras(bundle);
//                        setResult(RESULT_OK, intent);
//                        finish();
                        Toast.makeText(Editor_Location.this,
                                "You selected is " + datalist.get(position).getString(), Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.setOnClickListener(R.id.btnTop, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("---", "你选择的是置顶");
                        StringItem stringItem = datalist.get(position);
                        if (stringItem.getTop() == 0) {
                            stringItem.setTop(1);
                            MainActivity.setTopFragment(position, 1);
                            weatherDB.UpdataStringItem(stringItem,new StringItem(stringItem.getString(),stringItem.getTime(),1));
                        } else {
                            stringItem.setTop(0);
                            MainActivity.setTopFragment(position, 0);
                            weatherDB.UpdataStringItem(stringItem,new StringItem(stringItem.getString(),stringItem.getTime(),0));
                        }
                        SwipeMenuLayout.getViewCache().smoothClose();

                        //sort and update
                        Collections.sort(datalist);
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

                        MainActivity.removeCityFragment(i);

                        weatherDB.DelStringItem(datalist.get(position));
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
                    assert bundle != null;
                    String string = bundle.getString("city_name");

                    MainActivity.addCityFragment(string, 0, System.currentTimeMillis());
                    StringItem stringItem = new StringItem(string, System.currentTimeMillis());
                    datalist.add(stringItem);
                    Collections.sort(datalist);
                    myeditorAdapter.notifyDataSetChanged();

                    weatherDB.saveStringItem(stringItem);
                }
                break;
            default:
                break;
        }
    }

    private void initloading() {
        weatherDB = WeatherDB.getWeatherDB(this);
        datalist.addAll(weatherDB.loadStringItem());
        Collections.sort(datalist);
        myeditorAdapter.notifyDataSetChanged();
    }
}
