package com.example.movie.recommand.recommend.similarityCalutor;

import com.example.movie.recommand.recommend.model.Similarity;

import java.util.Set;

/**
 * @author dd
 * @Date 2022/7/29-16:48
 * @function
 */
public interface SimilarityCalculator{
    public Set<Similarity> calutorsSimilarity();
}
