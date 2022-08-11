package com.example.movie.recommand.recommend.respository;

import com.example.movie.recommand.recommend.model.RatingItem;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author dd
 * @Date 2022/7/29-20:56
 * @function
 */
public class UserRatingMapperDaoImpl implements UserRatingMapperDao {
    private static String driver = "com.mysql.jdbc.Driver";
    private  static String url = "jdbc:mysql://localhost:3306/movie_rc_test";
    private static String user = "root";
    private static String password = "123456";
    private static Connection connection=null;
    private static Statement statement=null;
    private static ResultSet resultSet=null;
    @Override
    public Map<Integer, Map<Integer, RatingItem>> getCommonUserItemsUserRatingsMap(Integer itemi, Integer itemj) {

        String findCommonUserSql="select * from rating where itemid ='"+itemj+"'and usrid in (select usrid from rating where itemid ='"
                +itemi+"')";
        Integer userid=null;
        Integer itemid=null;
        Integer rating=null;
        Integer RatingItem=null;
        Map<Integer,RatingItem>singleUserRatingMap=null;
        Map<Integer,Map<Integer,RatingItem>> usersRatingMap=new HashMap<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(connection==null){
            connection=DriverManager.getConnection(url,user,password);
            }
            statement=connection.createStatement();
            resultSet=statement.executeQuery(findCommonUserSql);
            while (resultSet.next()){
                userid=resultSet.getInt("usrid");
                itemid=resultSet.getInt("itemid");
                rating=resultSet.getInt("rating");
                singleUserRatingMap=usersRatingMap.get(userid);
                if(singleUserRatingMap==null) {
                    singleUserRatingMap = new HashMap<>();
//                    usersRatingMap.put(userid,singleUserRatingMap);
                }
                RatingItem ratingItem=new RatingItem();
                ratingItem.setItemId(itemid);
                ratingItem.setRating(rating);
                ratingItem.setUserId(userid);
                singleUserRatingMap.put(itemid,ratingItem);
                usersRatingMap.put(userid,singleUserRatingMap);

            }
            Object[]keyArray=usersRatingMap.keySet().toArray();
            for(int i=0;i<keyArray.length;i++){

                if(usersRatingMap.get(keyArray[i]).size()==1){
                    usersRatingMap.remove(keyArray[i]);
                }

            }
          //  System.out.println(usersRatingMap.get(256));

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

        if (usersRatingMap.size()>0)
            return usersRatingMap;
            return null;
    }
    @Override
    public Set<Integer> getItemSet() {
        String getItemSetSql="select distinct itemId from rating";
        Integer itemid=null;
        Set<Integer> itemsSet=new HashSet<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection=DriverManager.getConnection(url,user,password);
            statement=connection.createStatement();
            resultSet=statement.executeQuery(getItemSetSql);
            while (resultSet.next()){
                itemid=resultSet.getInt("itemid");
                itemsSet.add(itemid);
            }
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
        if(itemsSet.size()>0)
            return  itemsSet;
        return null;
    }
   public Map<Integer,Map<Integer,RatingItem>> getUserRatingMapperByItemSet(Set<Integer> itemSet){
            Object[]itemSetArray=itemSet.toArray();
        StringBuffer stringBuffer=new StringBuffer();
           stringBuffer.append("(");
           for(int i=0;i<itemSetArray.length-1;i++){
               stringBuffer.append(itemSetArray[i]+",");
           }
           stringBuffer.append(itemSetArray[itemSetArray.length-1]+")");
           String findSql="select * from rating where itemid in "+stringBuffer.toString();
           System.out.println(findSql);
       Map<Integer,Map<Integer,RatingItem>> usersRatingMapper=new HashMap<>();
           try {
               Class.forName("com.mysql.jdbc.Driver");
               if(connection==null){
                   connection= DriverManager.getConnection(url,user,password);
               }
               statement=connection.createStatement();
               resultSet=statement.executeQuery(findSql);
               Integer userid=null;
               Integer rating=null;
               Integer itemid=null;
               RatingItem ratingItem=null;
               Map<Integer,RatingItem> singleUserRatingMap=null;
               while (resultSet.next()){
                   ratingItem=new RatingItem();
                   userid=resultSet.getInt("usrid");
                   rating=resultSet.getInt("rating");
                    itemid=resultSet.getInt("itemid");
                    ratingItem.setItemId(itemid);
                    ratingItem.setRating(rating);
                    ratingItem.setUserId(userid);
                    singleUserRatingMap=usersRatingMapper.get(userid);
                    if(singleUserRatingMap==null)
                        singleUserRatingMap=new HashMap<>();
                    singleUserRatingMap.put(itemid,ratingItem);
                    usersRatingMapper.put(userid,singleUserRatingMap);
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
           if(usersRatingMapper.size()>0)
               return  usersRatingMapper;

           //System.out.println();
           return null;


   }
}
