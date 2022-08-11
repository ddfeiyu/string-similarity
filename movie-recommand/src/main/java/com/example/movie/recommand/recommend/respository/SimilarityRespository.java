package com.example.movie.recommand.recommend.respository;

import com.example.movie.recommand.recommend.model.Similarity;
import com.example.movie.recommand.recommend.model.SimilarityAndScore;

import java.util.Map;
import java.util.Set;

/**
 * @author dd
 * @Date 2022/7/29-18:01
 * @function
 */
public  interface SimilarityRespository  {
    //按照ItemId获取数据库当中与该ItemId有相似度的相似度数据.（自身.以及用户已经评分过的项目除外）
    public Map<Integer,Map<Integer,Double>> getSimilarityToUserIermSTOPK(Integer userId);
    public  Map<Integer,Map<Integer,SimilarityAndScore>> getSimilarityAndScoreToUserItemSTOPK(Integer userId);
    public void deleteAll();

    public Boolean saveSimilaritySet(Set<Similarity> similaritySet);





}
