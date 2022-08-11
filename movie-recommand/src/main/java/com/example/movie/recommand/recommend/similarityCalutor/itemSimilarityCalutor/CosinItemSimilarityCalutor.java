package com.example.movie.recommand.recommend.similarityCalutor.itemSimilarityCalutor;

import com.example.movie.recommand.recommend.model.RatingItem;
import com.example.movie.recommand.recommend.model.Similarity;
import com.example.movie.recommand.recommend.respository.UserRatingMapperDao;
import com.example.movie.recommand.recommend.respository.UserRatingMapperDaoImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author dd
 * @Date 2022/7/29-16:55
 * @function
 */
public class CosinItemSimilarityCalutor implements  ItemSimilarityCalculator {
    private UserRatingMapperDao userRatingMapperDao=new UserRatingMapperDaoImpl();

    public Set<Integer> getItemSet() {
        return itemSet;
    }

    public void setItemSet(Set<Integer> itemSet) {
        this.itemSet = itemSet;
    }

    private Set<Integer> itemSet;
    private Set<Similarity> similarityList=new HashSet<Similarity>();
    private  Map<Integer,Map<Integer,RatingItem>> userRatingMapper=null;
    private   Map<Similarity,Map<Integer,Map<Integer,RatingItem>>> itemMatrix= new HashMap<>();
    public Set<Similarity> calutorsSimilarity() {
        //获取要计算的物品集合
        if(itemSet==null){
        itemSet=userRatingMapperDao.getItemSet();
        userRatingMapper=userRatingMapperDao.getUserRatingMapperByItemSet(itemSet);
        }
        Object[]itemSetArray= itemSet.toArray();
        int itemSetSize=itemSet.size();
        Similarity similarity=null;
        for(int i=0;i<itemSetSize;i++){
            for(int j=i;j<itemSetSize;j++) {
                similarity = new Similarity();
                similarity.setItem1((int)itemSetArray[i]);
       //         System.out.println(i+"itemi:"+itemSetArray[i]);
                similarity.setItem2((int)itemSetArray[j]);
         //       System.out.println(j+"itemj:"+itemSetArray[j]);
            //    System.out.println(similarity);
                itemMatrix.put(similarity,null);
                //网格构建
                //寻找有共同评分的用户userId和用户的评分列表加入到矩阵网格i,j当中去。
                //数据格式{userId:{itemId:rating}}
                //方法一(从数据库当中针对每一个等待计算的item对获取共同用户的评分数据一次)：
                //数据库访问次数等于item对的计算次数
                // 花费时间13.22小时
//                Map<Integer,Map<Integer,RatingItem>> userRatingMapper=null;
//                userRatingMapper=userRatingMapperDao.getCommonUserItemsUserRatingsMap(similarity.getItem1(),similarity.getItem2());
//                itemMatrix.put(similarity,userRatingMapper);
            //    System.out.println("similarity"+similarity);
              //  System.out.println("----");
                //方法二按照itemSet寻找和itemSet有关的用户数据全部加入到内存当中
                //数据库只在计算时候访问一次
                // 时间花费 108秒
                for(Map.Entry<Integer,Map<Integer,RatingItem>> entry:userRatingMapper.entrySet()){
                    //取得 用户userId的{itemId:Rating}
                    Map<Integer,RatingItem> ratingItemMap=entry.getValue();
                    //获取用户id值
                    Integer z=entry.getKey();
                    //判断用户的{item：rating}是否包含对物品i和物品j的评分
                    //包含则取出user评分的字典，将当前用户加入字典当中去。
                    if(ratingItemMap.containsKey(similarity.getItem1())&&ratingItemMap.containsKey(similarity.getItem2())){
                        Map<Integer,Map<Integer,RatingItem>> ratingOfUser=itemMatrix.get(similarity);
                        if(ratingOfUser==null){
                            ratingOfUser=new HashMap<>();
                        }
                        ratingOfUser.put(z,ratingItemMap);
                        itemMatrix.put(similarity,ratingOfUser);

                    }


                }
                computeSimilarity(similarity);
                }
         //   similarityList.add(similarity);
           // itemMatrix.remove(similarity);
                //计算矩阵 i 和 j 的 相似度。
        System.out.println("还剩下"+(itemSetSize-i));
            }
            return itemMatrix.keySet();
        }
    private   void computeSimilarity(Similarity similarity){
        double ru=0;//用户的平均分
        double rui=0;//用户对i的评分
        double ruj=0;//用户对j的评分
        double fenzi=0;//分子部分
        double fomula1=0;//子公式1
        double fomula2=0;//子公式2；
        double fenmu1=0;//分母1;
        double  fenmu2=0;//分母2;
        double fenmu=0;//分母
        //求得网格当中的评分用户列表
        Map<Integer,Map<Integer,RatingItem>> usersList=itemMatrix.get(similarity);
        if(usersList!=null){
            //数据格式{userId:{itemId,rating}
            for(Map.Entry<Integer,Map<Integer,RatingItem>> entry2:usersList.entrySet()){
                //物品id,评分列表

                Map<Integer,RatingItem> ratingListOfUser=entry2.getValue();
                //用户评分物品的数量
                int ratingSize=0;
                //求用户评分的平均分
                int sum=0;
                for(Map.Entry<Integer,RatingItem> ratingEntry:ratingListOfUser.entrySet()){
                    sum+=ratingEntry.getValue().getRating();
                    ratingSize++;
                }

                //
                ru=sum/ratingSize;
//                if(entry2.getKey()==1){
//                    System.out.println("("+entry2.getKey()+"):"+ru);
//
//                }
                // System.out.println(similarity==null);
              //   System.out.println(ratingListOfUser.get((Integer)similarity.getItem1())==null);
//                 if(((Integer)similarity.getItem1())==null){
//          //       System.out.println("kkkkk");
//            //     System.out.println(similarity);
//                 }
                rui=ratingListOfUser.get(similarity.getItem1()).getRating();
                ruj=ratingListOfUser.get(similarity.getItem2()).getRating();
                fomula1=rui-ru;
                fomula2=ruj-ru;
                fenzi+=fomula1*fomula2;
                fenmu1+=Math.pow(fomula1,2);
                fenmu2+=Math.pow(fomula2,2);
            }
            fenmu=Math.sqrt(fenmu1)*Math.sqrt(fenmu2);
            if(fenmu!=0){
                similarity.setSimilarity(fenzi/fenmu);
            }
        }
        else
            similarity.setSimilarity(0);
    }
    public UserRatingMapperDao getUserRatingMapperDao() {
        return userRatingMapperDao;
    }
    public void setUserRatingMapperDao(UserRatingMapperDao userRatingMapperDao) {
        this.userRatingMapperDao = userRatingMapperDao;
    }
}
