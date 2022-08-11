package com.example.movie.recommand.service;

import com.example.movie.recommand.model.Movie;

import java.util.List;

/**
 * @author dd
 * @Date 2022/7/8-16:28
 * @function
 */
public interface IMovieService {
    public Movie addMovie(Movie movie);
    public List<Movie> listMovie();
    public  void deleteMovie(Integer movieId);

}
