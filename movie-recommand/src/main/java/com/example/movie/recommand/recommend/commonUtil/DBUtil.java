package com.example.movie.recommand.recommend.commonUtil;


import java.sql.*;

public class DBUtil {
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/movie_rc_test";
    private String user = "root";
    private String password = "123456";
    private Connection conn=null;
    private Statement statement=null;
    private ResultSet rs =null;
    private  static DBUtil dbUtil=null;
    private DBUtil(){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            //if(!conn.isClosed())
            // System.out.println("Succeeded connecting to the Database!");
            // statement???????SQL???
            statement = conn.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }//db()
    public static DBUtil  createDataSourc(){
        dbUtil=new DBUtil();
        return  dbUtil;
    }
    public DBUtil(String driver,String url,String user,String password){
        this.driver=driver;
        this.url=url;
        this.user=user;
        this.password=password;
        try {
            // ????????????
            Class.forName(driver);
            // ?????????
            conn = DriverManager.getConnection(url, user, password);
            if(!conn.isClosed())
                // System.out.println("Succeeded connecting to the Database!");
                // statement???????SQL???
                statement = conn.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }//db(String driver,String url,String user,String password)
    private void close_result(){
        try{
            if(!rs.isClosed()&&rs!=null)
                rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void close_state(){
        try{
            if(!statement.isClosed()&&statement!=null)
                statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void close_connect(){
        try{
            if(!conn.isClosed()&&conn!=null)
                conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public    void close(){
        close_result();
        close_state();
        close_connect();
    }
    public ResultSet executeQuery(String sql){
        try{
            rs= statement.executeQuery(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
    public void executeUpdate(String sql){
        try{
            statement.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
