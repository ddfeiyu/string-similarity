package com.example.movie.recommand.controller;

import com.example.movie.recommand.model.*;
import com.example.movie.recommand.respository.MovieRepository;
import com.example.movie.recommand.respository.MovieTagRepository;
import com.example.movie.recommand.respository.TagRepository;
import com.example.movie.recommand.respository.UserTagRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author dd
 * @Date 2022/7/2-11:34
 * @function
 */
@Controller
@RequestMapping("/user")
public class UserController  {

    @Autowired
    private UserTagRespository userTagRespository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieTagRepository movieTagRepository;
    @Autowired
    private TagRepository tagRepository;
    @PostMapping(value = "/chooseTag")
    public ModelAndView  saveChooseTag(String[] tags, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User user=(User) session.getAttribute("user");
        Integer userId= user.getId();
        if (tags != null) {
            for (String tag : tags) {
                System.out.println(tag);
                UserTag userTag=new UserTag();
                userTag.setName(tag);
                userTag.setUserId(userId);
                userTagRespository.save(userTag);
            }
        }
        modelAndView.setViewName("forward:/?userId="+user.getId());
        return  modelAndView;
    }



}
