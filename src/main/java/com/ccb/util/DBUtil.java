package com.ccb.util;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBUtil {

    public Connection getCon() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_boot", "dbboot", "dbboot123");
        return con;
    }


    public void closeCon(Connection con)throws Exception{
        if(con!=null){
            con.close();
        }
    }

    public static void main(String[] args) {
        DBUtil dbUtil=new DBUtil();
        try {
            dbUtil.getCon();
            System.out.println("连接成功!");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("连接失败");
        }
    }
}