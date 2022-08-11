package com.example.movie.recommand.controller;

import com.example.movie.recommand.model.Movie;
import com.example.movie.recommand.model.RatingItem;
import com.example.movie.recommand.model.User;
import com.example.movie.recommand.respository.MovieRepository;
import com.example.movie.recommand.respository.RatingItemRepository;
import com.example.movie.recommand.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @author dd
 * @Date 2022/7/26-12:34
 * @function
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    RatingItemRepository ratingItemRepository;
    @RequestMapping(value = "/addComment",method = RequestMethod.POST)
    public ModelAndView addComment(Integer movieId,Integer userId,Integer rating,String comment){
        ModelAndView modelAndView=new ModelAndView();
        //设置用户的评分项目
        RatingItem ratingItem=null;
        User user=userRepository.findOne(userId);
        ratingItem=ratingItemRepository.findByUser(user);
        Movie movie=movieRepository.findOne(movieId);
        if(ratingItem==null){
            ratingItem=new RatingItem();
            ratingItem.setUser(user);
            ratingItem.setMovie(movie);
        }
        ratingItem.setComment(comment);
        ratingItem.setCommentDate(new Date());
        ratingItem.setRating(rating);
        ratingItemRepository.save(ratingItem);
        Pageable pageable=new PageRequest(0,2);
        Page<RatingItem> pageBean=ratingItemRepository.findByMovie(movie,pageable);
        modelAndView.addObject("ratingItemPageBean",pageBean);
        modelAndView.setViewName("/front/movie/movieDetail::#ratingListContent");
        return  modelAndView;
    }

    @RequestMapping("/avg/{userId}")
    @ResponseBody
    public  Object[]  getAvarage(Integer userId){
        return  ratingItemRepository.getAvgeRating();
    }
}
