package com.example.movie.recommand.recommend.model;

import lombok.Data;

/**
 * @author dd
 * @Date 2022/7/2-19:18
 * @function
 */
@Data
public class SimilarityAndScore {
    private double similarity;
    private  Integer score;
}
