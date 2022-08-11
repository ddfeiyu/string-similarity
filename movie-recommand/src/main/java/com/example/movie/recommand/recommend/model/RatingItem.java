package com.example.movie.recommand.recommend.model;

import lombok.Data;

/**
 * @author dd
 * @Date 2022/7/27-22:44
 * @function
 */
@Data
public class RatingItem {
    private Integer itemId;
    private Integer userId;
    private  Integer rating;
}
