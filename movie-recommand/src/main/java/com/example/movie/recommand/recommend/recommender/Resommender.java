package com.example.movie.recommand.recommend.recommender;

import com.example.movie.recommand.recommend.predictor.Predictor;
import com.example.movie.recommand.recommend.similarityCalutor.SimilarityCalculator;

/**
 * @author dd
 * @Date 2022/7/29-20:21
 * @function
 */
public interface Resommender {
    public  void setSimilarityCalutor(SimilarityCalculator similarityCalutor);
    public void setPredictor(Predictor predictor);
    public  void recommend();

}
