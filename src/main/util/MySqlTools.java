package main.util;




import main.Module.Log;
import main.Module.LogAction;

import java.io.UnsupportedEncodingException;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Feng on 2017/6/15.
 */

public class MySqlTools {
    private String dbDriver="com.mysql.jdbc.Driver";
    //database?useUnicode=true&amp;characterEncoding=UTF-8
    private String dbUrl="jdbc:mysql://boteteam.com:3306/lizhidy?useUnicode=true&amp;characterEncoding=UTF-8";//根据实际情况变化
    private String dbUser="root";
    private String dbPass="123456";
    private Connection conn=null;
    public Connection getConn()
    {

        try
        {
            Class.forName(dbDriver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            conn = DriverManager.getConnection(dbUrl,dbUser,dbPass);//注意是三个参数
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConn(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int insertLoginNameDate(Log log)
    {
        int i=0;
        String sql="insert into loginlog(lid,stime,etime,duringtime,lname,uuid,gradeclassname,usertype) values(?,?,?,?,?,?,?,?)";

        try{

            PreparedStatement preStmt =conn.prepareStatement(sql);
            preStmt.setString(1,log.getLid());
            preStmt.setTimestamp(2,log.getStime());
            preStmt.setTimestamp(3,log.getEtime());
            preStmt.setLong(4,log.getDtime());
            preStmt.setString(5,log.getLname());
            preStmt.setString(6,log.getUuid().toString());
            preStmt.setString(7,log.getgradeClassName());
            preStmt.setString(8,"教师");

            i=preStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return i;//返回影响的行数，1为执行成功
    }

    public  int insertActionLog(LogAction logAction)
    {
        int i=0;
        String sql="insert into actionlog(actioname,actiontime,actionpeoplename,actionpeopleid,actiongradeclassname,usertype) values(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,logAction.getActionname());
            preparedStatement.setTimestamp(2,logAction.getActiontime());
            preparedStatement.setString(3,logAction.getActionpeoplename());
            preparedStatement.setString(4,logAction.getActionpeopleid());
            preparedStatement.setString(5,logAction.getActiongradeclassname());
            preparedStatement.setString(6,"教师");
            i=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  i;
    }

    public List<Log> queryLogTea(){
        ArrayList<Log> logs=new ArrayList<>();

        try {
            Statement statement=conn.createStatement();
            String sql="select * from loginlog where usertype='教师'  ORDER BY  gradeclassname,lid,stime  ";
            ResultSet res=statement.executeQuery(sql);
            while (res.next())
            {
                Log log=new Log(UUID.fromString(res.getString(7)));
                log.setLid(res.getString(2));
                log.setStime(res.getTimestamp(3));
                log.setEtime(res.getTimestamp(4));
                log.setDtime(res.getLong(5));
                log.setLname(res.getString(6));
                log.setgradeClassName(res.getString(8));
                logs.add(log);
            }
            res.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    public List<LogAction> queryLogAction(){
        ArrayList<LogAction> logActions=new ArrayList<>();
        try {
            Statement statement=conn.createStatement();
            String sql="select * from actionlog ORDER by actiongradeclassname,actioname,actiontime";
            ResultSet res=statement.executeQuery(sql);
            while (res.next())
            {
                LogAction logAction=new LogAction();
                logAction.setActiongradeclassname(res.getString(6));
                logAction.setActionname(res.getString(2));
                logAction.setActiontime(res.getTimestamp(3));
                logAction.setActionpeoplename(res.getString(4));
                logAction.setActionpeopleid(res.getString(5));
                logActions.add(logAction);
            }
            res.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return  logActions;
    }


}
