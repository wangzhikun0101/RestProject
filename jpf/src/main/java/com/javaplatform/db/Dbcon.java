package com.javaplatform.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dbcon {

    /**
     * 读取数据库相关信息
     * @return
     */

    public String getReaderFlow(DbconM dbm)
    {
        String strReturn = "";
        try {
            String url = "jdbc:sqlserver://"+dbm.sql_ip+";DatabaseName="+dbm.sql_dbname+";";
            Connection conn = DriverManager.getConnection(url, dbm.sql_id, dbm.sql_pwd);// 连接数据库
            Statement stat = conn.createStatement();//创建一个 Statement 对象来将 SQL 语句发送到数据库。
            ResultSet resultSet = stat.executeQuery(dbm.sql_select+dbm.sql_condion);
            if (resultSet.next()) {
                strReturn = resultSet.getString(dbm.sql_colname);

                //关闭数据库
                resultSet.close();
                stat.close();
                conn.close();
                return strReturn;

            } else {
                return strReturn;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return ex.toString();
        }

    }





}
