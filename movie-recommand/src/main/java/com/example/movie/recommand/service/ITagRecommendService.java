package com.example.movie.recommand.service;

import com.example.movie.recommand.model.Movie;

import java.util.List;

/**
 * @author dd
 * @Date 2022/7/2-14:34
 * @function
 */
public interface ITagRecommendService {
    public List<Movie> recommend(Integer userId);
}
