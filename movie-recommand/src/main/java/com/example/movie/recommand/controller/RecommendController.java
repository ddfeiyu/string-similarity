package com.example.movie.recommand.controller;

import com.example.movie.recommand.model.Movie;
import com.example.movie.recommand.model.User;
import com.example.movie.recommand.service.ITagRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author dd
 * @Date 2022/7/27-11:02
 * @function
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController  {
    @Autowired
    private ITagRecommendService iTagRecommendService;
    @RequestMapping(value = "/recommenderBytag",method = RequestMethod.GET)
    public ModelAndView recommendByTag( HttpSession session){
        System.out.println("---------");
        ModelAndView modelAndView=new ModelAndView();
        User user=(User) session.getAttribute("user");
        List<Movie> recommendMovieByTagList=null;
        if(user!=null){
            Integer userId=user.getId();
            System.out.println("index"+userId);
            recommendMovieByTagList= iTagRecommendService.recommend(userId);
            System.out.println(userId);
            System.out.println(recommendMovieByTagList);
            modelAndView.addObject("recommendMovieListByTag",recommendMovieByTagList);
        }

        modelAndView.setViewName("index::#recommendMovieListByTag");
        return  modelAndView;
    }
}
