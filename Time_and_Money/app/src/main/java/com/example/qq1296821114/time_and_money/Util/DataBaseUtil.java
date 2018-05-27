package com.example.qq1296821114.time_and_money.Util;

import android.util.Log;

import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataBaseUtil {
    private static final String url="jdbc:jtds:sqlserver://140.143.133.36:1433;DatabaseName=时金";
    private static final String user="lhg";
    private static final String password="0313lhg.";
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


    public static void dataBase_register(final String insert, final DataBase_Listener dataBase__listener) {
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
                    dataBase__listener.finish(null);
                } catch (SQLException e) {
                    e.printStackTrace();
                    dataBase__listener.error();
                }
            }
        }).start();
    }

    public static void dataBase_Check(String name, final DataBase_Listener dataBase__listener) {

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
                    dataBase__listener.finish(result);
                } catch (SQLException e) {
                    dataBase__listener.error();
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void dataBase_upload(final MyDB myDB, final String users, final DataBase_Listener dataBase__listener)
    {
        final String delete="delete \n" +
                "from user_money\n" +
                "where person_name='yesbutter'";
        Log.e(TAG, "run: "+"i am cone in " );
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Money> list=  myDB.loadMoney_Day();
                Connection con = null;
                try {
                    Class.forName(drives);//加载驱动换成这个
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    con = DriverManager.getConnection(url, user, password);
                    PreparedStatement preparedStatement;
                    preparedStatement=con.prepareStatement(delete);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    Log.e(TAG, "run: "+"shanchu  in "+list.size() );
                    for(Money money:list)
                    {
                        String insert="INSERT INTO user_money (person_name,dates,bill,image_id,bill_type)VALUES('"+users+"','"+
                                money.getDate(1).toString()+"',"+money.getMoney()+","+money.getImageId()+",'"+money.getType()+"');";

                        preparedStatement=con.prepareStatement(insert);
//                        statement.executeQuery(insert);
                        Log.e(TAG, "run: "+"上传成功" );
                    }
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    con.close();
                    dataBase__listener.finish("");
                } catch (SQLException e) {
                    e.printStackTrace();
                    dataBase__listener.error();
                }
            }
        }).start();
    }

    public static void dataBase_download(final MyDB myDB, final String users, final DataBase_Listener dataBase__listener)
    {
        final String select="select *\n"
                +"from user_money\n"
                +"where person_name='"+users+"';";
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
                    ResultSet resultSet=statement.executeQuery(select);
                    Log.e(TAG, "run: "+"i am in" +resultSet.toString());
                    myDB.delMoneyall();
                    while (resultSet.next()) {
                        Money money=new Money();
                        money.setMoney(resultSet.getFloat("bill"));
                        money.setDate(resultSet.getString("dates").split("-"));
                        money.setImageId(resultSet.getInt("image_id"));
                        money.setType(resultSet.getString("bill_type"));

                        myDB.saveMoney(money);
                        Log.e(TAG, "run: "+"增加一条记录"+money.toString() );
                    }
                    con.close();
                    dataBase__listener.finish("");
                } catch (SQLException e) {
                    dataBase__listener.error();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void dataBase_SelectUser(final DataBase_Select_Listener dataBase_select_listener)
    {
        final String check="select person_name,person_password\n" +
                "from person\n";
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
                    List<User> list=new ArrayList<>();
                    con = DriverManager.getConnection(url, user, password);
                    Statement statement=con.createStatement();
                    ResultSet resultSet=statement.executeQuery(check);
                    String result = null;
                    while (resultSet.next()) {
                        User user = new User();
                        user.setUser(resultSet.getString("person_name").trim());
                        if(user.getUser().trim().equals("admin"))
                            continue;
                        user.setPassword(resultSet.getString("person_password"));
                        list.add(user);
                        result+=resultSet.getString("person_name");
                        result+= resultSet.getString("person_password");
                    }
                    Log.e(TAG, "run:"+result);
                    con.close();
                    dataBase_select_listener.finish(list);
                } catch (SQLException e) {
                    dataBase_select_listener.error();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void dataBase_DeleteUser(String name,final DataBase_Listener dataBase_listener)
    {
        final String delete="delete \n" +
                "from person\n"+
                "where person_name='"+name+"';";
        final String del="delete \n"+
                "from user_money\n"+
                "where person_name='"+name+"';";
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
                    PreparedStatement preparedStatement;
                    preparedStatement=con.prepareStatement(del);
                    preparedStatement.executeUpdate();
                    preparedStatement=con.prepareStatement(delete);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    con.close();
                    dataBase_listener.finish(null);
                } catch (SQLException e) {
                    dataBase_listener.error();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface DataBase_Select_Listener{
        void finish(List<User> list);
        void error();
    }
    public interface DataBase_Listener {
        void finish(String result);
        void error();
    }
}
