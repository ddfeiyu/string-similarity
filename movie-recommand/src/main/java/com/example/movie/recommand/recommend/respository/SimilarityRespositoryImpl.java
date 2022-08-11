package com.example.movie.recommand.recommend.respository;

import com.example.movie.recommand.recommend.model.RatingItem;
import com.example.movie.recommand.recommend.model.Similarity;
import com.example.movie.recommand.recommend.model.SimilarityAndScore;

import java.sql.*;
import java.util.*;

/**
 * @author dd
 * @Date 2022/7/30-9:55
 * @function
 */
public class SimilarityRespositoryImpl implements SimilarityRespository {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/movie_rc_test";
    private static String user = "root";
    private static String password = "123456";
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    @Override
    public Map<Integer, Map<Integer, Double>> getSimilarityToUserIermSTOPK(Integer userId) {

        String findFilterItemofUser="select * from rating where usrid='"+userId+"'and rating >"+2;
        Map<Integer,RatingItem> ratingItemOfUser=new HashMap<>();
        List<Integer> newItemsList=new LinkedList<>();
        Map<Integer,Map<Integer,Double>> similarityRatingPredictorMap=new HashMap<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, password);
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(findFilterItemofUser);

            //  System.out.println(usersRatingMap.get(256));
            Integer itemId=null;
            Integer usrId=null;
            Integer rating=null;
            RatingItem ratingItem=null;
            while (resultSet.next()){
                ratingItem=new RatingItem();
                itemId=resultSet.getInt("itemid");
                userId=resultSet.getInt("usrid");
                rating=resultSet.getInt("rating");
                ratingItem.setUserId(usrId);
                ratingItem.setRating(rating);
                ratingItem.setItemId(itemId);
                newItemsList.add(itemId);
                ratingItemOfUser.put(itemId,ratingItem);
            }
            if(resultSet!=null)
                resultSet.close();
            if(statement!=null)
                statement.close();
            int newItemSize=newItemsList.size();
            if(newItemSize>0){
                String findTopKSql=null;
                Similarity similarity=null;
                int similarityItemId;
                Map<Integer,RatingItem> ratingItemMapOfSimilarity=null;
                 statement=connection.createStatement();
                Integer userItemId=null;
                Double simlitaryOfItems=null;
                Integer currrentItemRating=null;
                Double predictor=null;
                Map<Integer,Double> predictorItemMap=null;
                for(int i=0;i<newItemSize;i++){
                    //对过滤后的物品的每一项目取前4个。
                    userItemId=newItemsList.get(i);
                     findTopKSql="select * from sim where midi ='"+userItemId+"'and midj not in (select itemid from rating where usrid='"+userId+"' and rating>2)"+"UNION "+
                             "select * from sim where midj ='"+userItemId+"'and midi not in (select itemid from rating where usrid='"+userId+"' and rating>2)   order by sim DESC  limit 0,3";
                    // System.out.println(findTopKSql);
                     resultSet=statement.executeQuery(findTopKSql);
                     while (resultSet.next()){
                        similarityItemId=resultSet.getInt("midi");
                        if(similarityItemId==userItemId){
                            similarityItemId=resultSet.getInt("midj");
                        }
                        simlitaryOfItems=resultSet.getDouble("sim");
                        currrentItemRating= ratingItemOfUser.get(itemId).getRating();
                         predictor=currrentItemRating*simlitaryOfItems;
                         predictorItemMap=similarityRatingPredictorMap.get(similarityItemId);
                         if(predictorItemMap==null)
                             predictorItemMap=new HashMap<>();
                         predictorItemMap.put(itemId,predictor);
                         similarityRatingPredictorMap.put(similarityItemId,predictorItemMap);
                     }


                }
                if(resultSet==null)
                    resultSet.close();
                if (statement==null)
                    statement.close();
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
            if(similarityRatingPredictorMap.size()>0)
                return  similarityRatingPredictorMap;
        return  null;
    }

    @Override
    public Map<Integer, Map<Integer, SimilarityAndScore>> getSimilarityAndScoreToUserItemSTOPK(Integer userId) {


        String findFilterItemofUser="select * from rating where usrid='"+userId+"'and rating >"+2;
        Map<Integer,RatingItem> ratingItemOfUser=new HashMap<>();
        List<Integer> newItemsList=new LinkedList<>();
        Map<Integer,Map<Integer,SimilarityAndScore>> similarityRatingPredictorMap=new HashMap<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, password);
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(findFilterItemofUser);

            //  System.out.println(usersRatingMap.get(256));
            Integer itemId=null;
            Integer usrId=null;
            Integer rating=null;
            RatingItem ratingItem=null;
            while (resultSet.next()){
                ratingItem=new RatingItem();
                itemId=resultSet.getInt("itemid");
                userId=resultSet.getInt("usrid");
                rating=resultSet.getInt("rating");
                ratingItem.setUserId(usrId);
                ratingItem.setRating(rating);
                ratingItem.setItemId(itemId);
                newItemsList.add(itemId);
                ratingItemOfUser.put(itemId,ratingItem);
            }
            if(resultSet!=null)
                resultSet.close();
            if(statement!=null)
                statement.close();
            int newItemSize=newItemsList.size();
            if(newItemSize>0){
                String findTopKSql=null;
                Similarity similarity=null;
                int similarityItemId;
                Map<Integer,RatingItem> ratingItemMapOfSimilarity=null;
                statement=connection.createStatement();
                Integer userItemId=null;
                Double simlitaryOfItems=null;
                Integer currrentItemRating=null;
                Double predictor=null;
                Map<Integer,SimilarityAndScore>  similarityAndScoreMap=null;
                for(int i=0;i<newItemSize;i++){
                    //对过滤后的物品的每一项目取前4个。
                    userItemId=newItemsList.get(i);
                    findTopKSql="select * from sim where midi ='"+userItemId+"'and midj not in (select itemid from rating where usrid='"+userId+"' and rating>2)"+"UNION "+
                            "select * from sim where midj ='"+userItemId+"'and midi not in (select itemid from rating where usrid='"+userId+"' and rating>2)   order by sim DESC  limit 0,3";
                    // System.out.println(findTopKSql);
                    resultSet=statement.executeQuery(findTopKSql);
                    while (resultSet.next()){
                        similarityItemId=resultSet.getInt("midi");
                        if(similarityItemId==userItemId){
                            similarityItemId=resultSet.getInt("midj");
                        }
                        simlitaryOfItems=resultSet.getDouble("sim");
                        currrentItemRating= ratingItemOfUser.get(userItemId).getRating();
                        SimilarityAndScore similarityAndScore=new SimilarityAndScore();
                        similarityAndScore.setScore(currrentItemRating);
                        similarityAndScore.setSimilarity(simlitaryOfItems);
                        similarityAndScoreMap=similarityRatingPredictorMap.get(similarityItemId);
                        if(similarityAndScoreMap==null)
                            similarityAndScoreMap=new HashMap<>();
                        similarityAndScoreMap.put(userItemId,similarityAndScore);
                        similarityRatingPredictorMap.put(similarityItemId,similarityAndScoreMap);
                    }


                }
                if(resultSet==null)
                    resultSet.close();
                if (statement==null)
                    statement.close();
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(similarityRatingPredictorMap.size()>0)
            return  similarityRatingPredictorMap;
        return  null;
    }

    public Boolean saveSimilaritySet(Set<Similarity> similaritySet){
        String addSetUrl = "jdbc:mysql://localhost:3306/movie_rc_test?rewriteBatchedStatements=true";
        try {
            connection=DriverManager.getConnection(addSetUrl,user,password);
            String addSql="insert into sim  去··     (midi,midj,sim) values(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(addSql);
            for(Similarity similarity:similaritySet){

                preparedStatement.setInt(1,similarity.getItem1());
                preparedStatement.setInt(2,similarity.getItem2());
                preparedStatement.setDouble(3,similarity.getSimilarity());
           //     preparedStatement.setString(4,new Date(System.currentTimeMillis()).toString());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            if(resultSet!=null)
                resultSet.close();
            if(statement!=null)
                statement.close();
            if(preparedStatement!=null)
                preparedStatement.close();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  false;
    }
    @Override
    public void deleteAll() {
        String deleteAvgRatingSql="delete from sim";
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
}