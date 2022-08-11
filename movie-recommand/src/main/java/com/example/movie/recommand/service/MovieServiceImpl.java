package com.example.movie.recommand.service;


import com.example.movie.recommand.model.Movie;
import com.example.movie.recommand.respository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dd
 * @Date 2022/7/8-16:39
 * @function
 */
@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    MovieRepository movieRepository;
    @Override
    public Movie addMovie(Movie movie) {
        movie.setCreatedate(new Date());
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public List<Movie> listMovie() {
        return (List<Movie>)movieRepository.findAll();
    }

    @Override
    public void deleteMovie(Integer movieId) {
        movieRepository.delete(movieId);
    }

}
