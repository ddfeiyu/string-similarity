package com.example.movie.recommand.recommend.respository;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author dd
 * @Date 2022/7/29-23:14
 * @function
 */
public class AvgRatingRespositoryImpl implements AvgRatingRespository {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/movie_rc_test";
    private static String user = "root";
    private static String password = "123456";
    private static Connection connection=null;
    private static Statement statement=null;
    private static ResultSet resultSet=null;
    @Override
    public Double findByUserId(Integer userId) {
        List<Integer> userList=new LinkedList<>();
        userList.add(userId);
        Map<Integer,Double> userAvgeRating=findAvgRatingMapByUserIdList(userList);
        if(userAvgeRating!=null)
            return (Double)userAvgeRating.get(userId);
        return null;
    }

    @Override
    public void deleteAll() {
    String deleteAvgRatingSql="delete from avgrating";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(connection==null){
                connection= DriverManager.getConnection(url,user,password);
            }
            statement=connection.createStatement();
            statement.executeUpdate(deleteAvgRatingSql);
            if(resultSet!=null)
                resultSet.close();
            if(statement!=null);
            statement.close();
            //if(connection!=null)
            //  connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public Map<Integer,Double> findAvgRatingMapByUserIdList(List<Integer> userIdlist) {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("(");
        for(int i=0;i<userIdlist.size()-1;i++){
            stringBuffer.append(userIdlist.get(i)+",");
        }
        stringBuffer.append(userIdlist.get(userIdlist.size()-1)+")");
        String findSql="select * from avgrating where usrid in "+stringBuffer.toString();
        System.out.println(findSql);
        Map<Integer,Double> avageRatingMap=new HashMap<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(connection==null){
                connection= DriverManager.getConnection(url,user,password);
            }
            statement=connection.createStatement();
            resultSet=statement.executeQuery(findSql);
            Integer userid=null;
            Double avgRating=null;

            while (resultSet.next()){
                userid=resultSet.getInt("usrid");
                avgRating=resultSet.getDouble("avgRating");
                avageRatingMap.put(userid,avgRating);
            }
            if(resultSet!=null)
                resultSet.close();
            if(statement!=null);
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(avageRatingMap.size()>0)
            return  avageRatingMap;

        //System.out.println();
        return null;
    }

    @Override
    public void generateAll() {

        String insertAvageRatingSql="insert into avgrating SELECT usrid,avg(rating) avgRating FROM rating group by usrid";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(connection==null){
                connection= DriverManager.getConnection(url,user,password);
            }
            statement=connection.createStatement();
            statement.executeUpdate(insertAvageRatingSql);
            if(resultSet!=null)
                resultSet.close();
            if(statement!=null);
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
