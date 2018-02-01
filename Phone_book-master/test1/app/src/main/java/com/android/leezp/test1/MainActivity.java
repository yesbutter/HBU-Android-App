package com.android.leezp.test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.leezp.test1.Adapter.ContactsListAdapter;
import com.android.leezp.test1.Entity.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    //页面上的子控件
    private TextView personNum;
    private TextView groupId;
    private ListView contacts;

    //ListView上的子项的显示
    private List<Person> personList;
    private ContactsListAdapter adapter;

    //模拟数据源
    private String[] name = {"马云","刘东强","李彦宏","马化腾","丁磊","雷军","侯亮平","李达康","沙瑞金","高育良","祁同伟","陈海","陈岩石","欧阳菁","易学习","王大路","高小琴","高小凤","林丹"};
    private String[] telephone = {"18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645","18485958645"};

    //上一次groudId控件上的元素值
    private String lastGroudId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        initView();

        initEvent();
    }

    /**
     * 初始化页面上的控件
     */
    private void initView() {
        personNum = (TextView) findViewById(R.id.activity_main_personNum);
        groupId = (TextView) findViewById(R.id.activity_main_groupId);
        contacts = (ListView) findViewById(R.id.activity_main_contacts);
    }

    /**
     * 将控件上的事件确定
     */
    private void initEvent() {
        setData();
        adapter = new ContactsListAdapter(this, personList);
        contacts.setAdapter(adapter);
        setListViewonScroll();

        personNum.setText(""+Person.personNum);

        contacts.setOnItemClickListener(this);
    }

    /**
     * 设置数据源
     */
    private void setData() {
        personList = new ArrayList<>();

        for (int i=0;i<name.length;++i) {
            Person p = new Person(name[i],telephone[i]);
            Person.personNum++;
            personList.add(p);
        }

        Collections.sort(personList);
    }

    /**
     * 给ListView设置滑动事件，让其顶上的字母显示正在滑动组的字母
     */
    private void setListViewonScroll() {
        contacts.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Person person = personList.get(firstVisibleItem);
                if (!person.getFirstLetter().equals(lastGroudId)) {
                    groupId.setText(person.getFirstLetter());
                }
                lastGroudId = person.getFirstLetter();
            }
        });
    }

    /**
     * ListView的点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ItemActivity.class);
        Person person = personList.get(position);
        intent.putExtra("person", person);
        startActivity(intent);
    }
}
