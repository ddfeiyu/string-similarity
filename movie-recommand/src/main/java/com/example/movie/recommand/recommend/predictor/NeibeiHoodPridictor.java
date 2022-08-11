package com.example.movie.recommand.recommend.predictor;

import com.example.movie.recommand.recommend.model.RecommendItem;
import com.example.movie.recommand.recommend.model.SimilarityAndScore;
import com.example.movie.recommand.recommend.respository.SimilarityRespository;
import com.example.movie.recommand.recommend.respository.SimilarityRespositoryImpl;
import com.example.movie.recommand.recommend.respository.UserRatingMapperDao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * @author dd
 * @Date 2022/7/29-18:12
 * @function
 */
public class NeibeiHoodPridictor implements Predictor {
    private UserRatingMapperDao userRatingMapperDao;
    private SimilarityRespository similarityRespository=new SimilarityRespositoryImpl();
    private List<RecommendItem> recommendItemList=new LinkedList<>();
    @Override
    public void predictor(Integer userId) {

      Map<Integer,Map<Integer,SimilarityAndScore>> userSimilarityMap=similarityRespository.getSimilarityAndScoreToUserItemSTOPK(userId);
            for(Map.Entry<Integer,Map<Integer,SimilarityAndScore>> entry:userSimilarityMap.entrySet()){
                Integer recommendId=entry.getKey();
                double predictorRating=0;
                double totalSimilarity=0;
                StringBuffer recommendReasonIds=new StringBuffer();
                Map<Integer,SimilarityAndScore> predictorItemMap=entry.getValue();
                for(Map.Entry<Integer,SimilarityAndScore> pridctoEntry:predictorItemMap.entrySet()){
                    predictorRating+=pridctoEntry.getValue().getScore()*pridctoEntry.getValue().getSimilarity();
                    totalSimilarity+=pridctoEntry.getValue().getSimilarity();
                    recommendReasonIds.append(pridctoEntry.getKey()+",");
                }
                predictorRating=predictorRating/totalSimilarity;
                RecommendItem recommendItem=new RecommendItem();
                recommendItem.setPredictorRating(predictorRating);
                recommendItem.setRecommendItemId(recommendId);
                recommendItem.setReacommendReasonIds(recommendReasonIds.toString());
                recommendItemList.add(recommendItem);
            }
            for(Object o:recommendItemList){
                System.out.println("------------------------------------");
                System.out.println(((RecommendItem) o).getRecommendItemId()+"预测评分"+((RecommendItem)o).getPredictorRating());
                System.out.println("推荐理由："+((RecommendItem)o).getReacommendReasonIds());
                System.out.println("------------------------------------");
            }
            System.out.println(recommendItemList.size());


    }
}
