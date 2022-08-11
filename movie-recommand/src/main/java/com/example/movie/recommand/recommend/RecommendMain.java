package com.example.movie.recommand.recommend;

import com.example.movie.recommand.recommend.model.RatingItem;
import com.example.movie.recommand.recommend.predictor.NeibeiHoodPridictor;
import com.example.movie.recommand.recommend.predictor.Predictor;
import com.example.movie.recommand.recommend.respository.*;
import com.example.movie.recommand.recommend.similarityCalutor.SimilarityCalculator;
import com.example.movie.recommand.recommend.similarityCalutor.itemSimilarityCalutor.CosinItemSimilarityCalutor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dd
 * @Date 2022/7/29-20:46
 * @function
 */
public class RecommendMain {
    private   static long begin;
    private   static  long end;
    public static  void begin(){
        begin=System.currentTimeMillis()/1000;
    }
    public static void end(){
        end =System.currentTimeMillis()/1000;
    }
    public static  String getCosume(){
        Long internal=end-begin;
//        Long minute=0L;
//        Long hour=0L;
//        internal=internal%60;
//        hour=minute/60;
//         minute=(internal/60)%60;
//        return  String.format("花费(%d时%d分%d秒)",hour,minute,internal);

        return  String.format("花费(%d秒)",internal);



    }
    public  static  void main(String[]args){
        test3();
      //  test4();
       // test5();
       test6();

        test7();


    }
    public static  void test7(){
        begin();
        Predictor predictor=new NeibeiHoodPridictor();
        predictor.predictor(1);
        end();
        System.out.println("花费"+getCosume()+"秒");
    }
    public static  void  test6(){
        begin();

        SimilarityCalculator similarityCalculator=new CosinItemSimilarityCalutor();
        Set<com.example.movie.recommand.recommend.model.Similarity> similaritySet = similarityCalculator.calutorsSimilarity();
       SimilarityRespository similarityRespository=new SimilarityRespositoryImpl();
       similarityRespository.saveSimilaritySet(similaritySet);
        end();
        System.out.println("花费"+getCosume()+"秒");

    }
    public  static  void  test5(){
        AvgRatingRespository avgRatingRespository=new AvgRatingRespositoryImpl();
        Double avgRating=avgRatingRespository.findByUserId(1);
        System.out.println("用户"+1+"平均分"+avgRating);
    }
    public static  void  test4(){
        AvgRatingRespository avgRatingRespository=new AvgRatingRespositoryImpl();
        List<Integer> userList=new LinkedList<>();
        userList.add(1);
        userList.add(2);
        Map<Integer,Double>  userAvgRatingMap=avgRatingRespository.findAvgRatingMapByUserIdList(userList);
    for(Map.Entry<Integer,Double> entry:userAvgRatingMap.entrySet()){
        System.out.println("用户"+entry.getKey()+"平均分"+entry.getValue());
    }
    }
    public static  void test3(){
        AvgRatingRespository avgRatingRespository=new AvgRatingRespositoryImpl();
        avgRatingRespository.deleteAll();
        avgRatingRespository.generateAll();
        SimilarityRespository similarityRespository=new SimilarityRespositoryImpl();
        similarityRespository.deleteAll();
    }
    public  static  void test2(){
        UserRatingMapperDao userRatingMapperDao=new UserRatingMapperDaoImpl();
        Set<Integer> itemSet=userRatingMapperDao.getItemSet();
        for(Object o:itemSet){
            System.out.println(o);
        }
        System.out.println("评分物品总数"+itemSet.size());
    }
    public  static  void  test1(){
        UserRatingMapperDao userRatingMapperDao=new UserRatingMapperDaoImpl();
        Map<Integer,Map<Integer,RatingItem>> usersRatingMapper=userRatingMapperDao.getCommonUserItemsUserRatingsMap(1,2);
        for (Map.Entry<Integer,Map<Integer,RatingItem>> entry:usersRatingMapper.entrySet()){
            System.out.println("用户");
            System.out.println(entry.getKey());
        }
        System.out.println("总人数"+usersRatingMapper.entrySet().size());
    }
}
