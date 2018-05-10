package com.qiaojim.bluetoothstudy;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/4.
 */
public class DataTransFragment extends Fragment {

    TextView connectNameTv;
    ListView showDataLv;
    EditText inputEt;
    Button sendBt;
    ArrayAdapter<String> dataListAdapter;

    MainActivity mainActivity;
    Handler uiHandler;

    BluetoothDevice remoteDevice;

    Button btn4, btn3, btn2, btn1;
    private int MODE = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_data_trans, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        connectNameTv = (TextView) view.findViewById(R.id.device_name_tv);
        showDataLv = (ListView) view.findViewById(R.id.show_data_lv);
        inputEt = (EditText) view.findViewById(R.id.input_et);
        sendBt = (Button) view.findViewById(R.id.send_bt);
        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msgSend = inputEt.getText().toString();
                Message message = new Message();
                message.what = Params.MSG_WRITE_DATA;
                message.obj = msgSend;
                uiHandler.sendMessage(message);

                inputEt.setText("");
            }
        });
        btn4 = (Button) view.findViewById(R.id.send_4);
        btn4.setOnClickListener(lisenter);
        btn3 = (Button) view.findViewById(R.id.send_3);
        btn3.setOnClickListener(lisenter);
        btn2 = (Button) view.findViewById(R.id.send_2);
        btn2.setOnClickListener(lisenter);
        btn1 = (Button) view.findViewById(R.id.send_1);
        btn1.setOnClickListener(lisenter);
        btn3.setVisibility(view.INVISIBLE);
        btn2.setVisibility(view.INVISIBLE);
        btn1.setVisibility(view.INVISIBLE);
        dataListAdapter = new ArrayAdapter<String>(getContext(), R.layout.layout_item_new_data);
        showDataLv.setAdapter(dataListAdapter);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        uiHandler = mainActivity.getUiHandler();


    }

    /**
     * 显示连接远端(客户端)设备
     */
    public void receiveClient(BluetoothDevice clientDevice) {
        this.remoteDevice = clientDevice;
        connectNameTv.setText("连接设备: " + remoteDevice.getName());
    }

    /**
     * 显示新消息
     *
     * @param newMsg
     */
    public void updateDataView(String newMsg, int role) {

        if (role == Params.REMOTE) {
            String remoteName = remoteDevice.getName() == null ? "未命名设备" : remoteDevice.getName();
            newMsg = remoteName + " : " + newMsg;
        } else if (role == Params.ME) {
            newMsg = "我 : " + newMsg;
        }
        dataListAdapter.add(newMsg);
    }

    /**
     * 客户端连接服务器端设备后，显示
     *
     * @param serverDevice
     */
    public void connectServer(BluetoothDevice serverDevice) {
        this.remoteDevice = serverDevice;
        connectNameTv.setText("连接设备: " + remoteDevice.getName());
    }

    private View.OnClickListener lisenter = new View.OnClickListener() {//侦听器

        @Override
        public void onClick(View view) {//点击事件

            switch (view.getId()) {

                case R.id.send_4:
                    String msgSend = "4";
                    Message message = new Message();
                    message.what = Params.MSG_WRITE_DATA;
                    message.obj = msgSend;
                    uiHandler.sendMessage(message);
                    MODE++;
                    if (MODE == 5)
                        MODE = 0;
                    break;
                case R.id.send_3:
                    msgSend = "3";
                    message = new Message();
                    message.what = Params.MSG_WRITE_DATA;
                    message.obj = msgSend;
                    uiHandler.sendMessage(message);
                    break;
                case R.id.send_2:
                    msgSend = "2";
                    message = new Message();
                    message.what = Params.MSG_WRITE_DATA;
                    message.obj = msgSend;
                    uiHandler.sendMessage(message);
                    break;
                case R.id.send_1:
                    msgSend = "1";
                    message = new Message();
                    message.what = Params.MSG_WRITE_DATA;
                    message.obj = msgSend;
                    uiHandler.sendMessage(message);
                    break;
                default:
                    break;
            }
            switch (MODE) {

                case 0:
                    btn1.setText("移位");
                    btn2.setText("增加");
                    btn3.setText("减少");
                    btn1.setVisibility(view.INVISIBLE);
                    btn2.setVisibility(view.INVISIBLE);
                    btn3.setVisibility(view.INVISIBLE);
                    break;
                case 1:
                    btn1.setText("移位");
                    btn2.setText("增加");
                    btn3.setText("减少");
                    btn1.setVisibility(view.VISIBLE);
                    btn2.setVisibility(view.VISIBLE);
                    btn3.setVisibility(view.VISIBLE);
                    break;
                case 2:
                    btn1.setText("计次");
                    btn2.setText("归零");
                    btn3.setText("减少");
                    btn1.setVisibility(view.VISIBLE);
                    btn2.setVisibility(view.VISIBLE);
                    btn3.setVisibility(view.INVISIBLE);
                    break;
                case 3:
                    btn1.setText("移位");
                    btn2.setText("增加");
                    btn3.setText("减少");
                    btn1.setVisibility(view.VISIBLE);
                    btn2.setVisibility(view.VISIBLE);
                    btn3.setVisibility(view.VISIBLE);
                    break;
                case 4:
                    btn1.setText("移位");
                    btn2.setText("开启/关闭");
                    btn3.setText("减少");
                    btn1.setVisibility(view.INVISIBLE);
                    btn2.setVisibility(view.VISIBLE);
                    btn3.setVisibility(view.INVISIBLE);
                    break;
                default:
                    break;
            }


            String msgSend = "0";
            Message message = new Message();
            message.what = Params.MSG_WRITE_DATA;
            message.obj = msgSend;
            uiHandler.sendMessage(message);

        }
    };

}
