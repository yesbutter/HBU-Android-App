package com.example.qq1296821114.time_and_money.Presenter.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qq1296821114.time_and_money.Adapter.AdminAdapter;
import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.User;
import com.example.qq1296821114.time_and_money.Presenter.Activity.MainActivity;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.Util.DataBaseUtil;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t-lidashao on 18-5-27.
 */

public class AdminFragment extends Fragment implements OnClickListener {

    private MyDB myDB;
    private List<User> list;
    private ListView listView;
    private AdminAdapter adminAdapter;

    @Override
    public void onClick(View view) {

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adminAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(getContext(),"删除用户成功",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getContext(),"删除用户失败，请刷新重试",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._admin_layout, container, false);
        init_view(view);


        return view;
    }


    private void init_view(View view) {
        listView = view.findViewById(R.id._admin_list);
        list = new ArrayList<>();
        DataBaseUtil.dataBase_SelectUser(new DataBaseUtil.DataBase_Select_Listener() {
            @Override
            public void finish(List<User> list1) {
                list.addAll(list1);
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void error() {

            }
        });
        adminAdapter = new AdminAdapter(getContext(), R.layout._admin_layout_item, list) {
            @Override
            public void btn_del(final int position) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("确认删除用户")//设置对话框的标题
                    .setMessage("确认删除之后，改用户的的所有信息都将删除！！！")//设置对话框的内容
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adminAdapter.notifyDataSetChanged();
                            SwipeMenuLayout.getViewCache().quickClose();
                            DataBaseUtil.dataBase_DeleteUser(list.get(position).getUser(), new DataBaseUtil.DataBase_Listener() {
                                @Override
                                public void finish(String result) {
                                    Message message=new Message();
                                    message.what=2;
                                    handler.sendMessage(message);
                                }

                                @Override
                                public void error() {
                                    Message message=new Message();
                                    message.what=3;
                                    handler.sendMessage(message);
                                }
                            });
                            list.remove(position);
                            dialog.dismiss();
                        }
                    }).create();
                dialog.show();

            }
        };
        listView.setAdapter(adminAdapter);
        adminAdapter.notifyDataSetChanged();

    }
}
