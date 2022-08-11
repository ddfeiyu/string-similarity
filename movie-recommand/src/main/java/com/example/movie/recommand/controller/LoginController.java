package com.example.movie.recommand.controller;

import com.example.movie.recommand.helper.*;
import com.example.movie.recommand.model.Tag;
import com.example.movie.recommand.model.User;
import com.example.movie.recommand.respository.TagRepository;
import com.example.movie.recommand.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author dd
 * @Date 2022/7/22-19:41
 * @function
 */
@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataHelper dataHelper;
    @Autowired
    private TagRepository tagRepository;
    //登录方法

    @RequestMapping("/user/login")
    public String Login(Model model){
        model.addAttribute("user",new User());
        return "/user/login";
    }
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)

    public ModelAndView Login(User user,HttpSession session){
        System.out.println(user);
        ModelAndView modelAndView=new ModelAndView();
        User userEntity = userRepository.findByNameAndPassword(user.getName(),user.getPassword());
        if (userEntity !=null){
            System.out.println("111111111");
            if(userEntity.getFirstLogin()){
                userEntity.setFirstLogin(false);
                userRepository.save(userEntity);
                session.setAttribute("user",userEntity);
                Sort sort=new Sort(Sort.Direction.ASC,"count");
                Pageable pageable= new PageRequest(0,20,sort);
                Page page=tagRepository.findAll(pageable);
                List<Tag> tagList=null;
                if(page!=null) {
                     tagList = page.getContent();
                }
                modelAndView.setViewName("/user/chooseTags");
                modelAndView.addObject("tagList",tagList);

            }
            else {
                session.setAttribute("user", userEntity);
                modelAndView.setViewName("forward:/?userId="+userEntity.getId());
            }

        }

        else
            modelAndView.setViewName("/user/loginError");
            return modelAndView;
    }
    @RequestMapping("user/logout")
    public String logout(Integer userId,HttpSession session){
        if (session.getAttribute("user")!=null){
        session.removeAttribute("user");
        }
        return "redirect:/";
    }
    @RequestMapping("/user/register")
    public String register(){

        return "/user/register";
    }
    @RequestMapping(value = "/user/register",method =RequestMethod.POST)

    public ModelAndView register(User user, String password2){
        ModelAndView modelAndView=new ModelAndView();
        if(user.getPassword().equals(password2)){
            User userInDatabase=userRepository.findByName(user.getName());
            if (userInDatabase==null) {
                userRepository.save(user);

                dataHelper.setMessager("注册成功");
                dataHelper.setStatus(true);
                dataHelper.setData(null);

            }
            else {
                dataHelper.setStatus(false);
                dataHelper.setMessager("用户名重复");
            }

        }
        else {
            dataHelper.setMessager("密码不一致，请返回校验密码");
        }
        modelAndView.setViewName("/user/registerInfo");
        modelAndView.addObject("registerData",dataHelper);

        return  modelAndView;

    }
}



