package com.example.qq1296821114.time_and_money.Util;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.content.ContentValues.TAG;

public class DataBaseUtil {
    private static final String url="jdbc:jtds:sqlserver://192.168.137.1:1433;DatabaseName=时金";
    private static final String user="users";
    private static final String password="0313lhg";
    private static final String drives="net.sourceforge.jtds.jdbc.Driver";
    public static Connection con=null;

    private static Connection getCon()
    {
        if(con==null)
        {
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");//加载驱动换成这个
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                con = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.111:1433;DatabaseName=时金",
                        "users", "0313lhg");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }


    public static void dataBase_register(final String insert, final dataBase_register_Listener dataBase_register_listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection con = null;
                try {
                    Class.forName(drives);//加载驱动换成这个
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    con = DriverManager.getConnection(url, user, password);
                    con.prepareStatement(insert).executeUpdate();
                    con.close();
                    dataBase_register_listener.finish(null);
                } catch (SQLException e) {
                    e.printStackTrace();
                    dataBase_register_listener.error();
                }
            }
        }).start();
    }

    public static void dataBase_Check(String name, final dataBase_register_Listener dataBase_register_listener) {

        final String check="select person_password\n" +
                "from person\n"
                +"where person_name='"+name+"';";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection con = null;
                try {
                    Class.forName(drives);//加载驱动换成这个
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    con = DriverManager.getConnection(url, user, password);
                    Statement statement=con.createStatement();
                    ResultSet resultSet=statement.executeQuery(check);
                    String result = null;
                    while (resultSet.next()) {
                        result= resultSet.getString("person_password");
                    }
                    int l=0;
                    if(result!=null) {
                        for (int i = result.length() - 1; i >= 0; i--) {
                            if (result.charAt(i) != ' ') {
                                l = i + 1;
                                break;
                            }
                        }
                        result = result.substring(0, l);
                    }
                    Log.e(TAG, "run:"+result);
                    con.close();
                    dataBase_register_listener.finish(result);
                } catch (SQLException e) {
                    dataBase_register_listener.error();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface dataBase_register_Listener {
        void finish(String result);
        void error();
    }
}
