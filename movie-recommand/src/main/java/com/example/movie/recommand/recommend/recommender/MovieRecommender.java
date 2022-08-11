package com.example.movie.recommand.recommend.recommender;

import com.example.movie.recommand.recommend.predictor.Predictor;
import com.example.movie.recommand.recommend.similarityCalutor.SimilarityCalculator;

/**
 * @author dd
 * @Date 2022/7/29-20:24
 * @function
 */
public class MovieRecommender implements Resommender {
    private SimilarityCalculator similarityCalculator;
    private Predictor predictor;


    @Override
    public void setSimilarityCalutor(SimilarityCalculator similarityCalutor) {
        this.similarityCalculator=similarityCalutor;
    }

    @Override
    public void setPredictor(Predictor predictor) {
        this.predictor=predictor;
    }

    @Override
    public void recommend() {
    }
}
