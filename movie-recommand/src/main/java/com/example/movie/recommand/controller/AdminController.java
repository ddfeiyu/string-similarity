package com.example.movie.recommand.controller;

import com.example.movie.recommand.model.MoiveTag;
import com.example.movie.recommand.model.Movie;
import com.example.movie.recommand.model.Tag;
import com.example.movie.recommand.respository.*;
import com.example.movie.recommand.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * @author dd
 * @Date 2022/7/6-19:52
 * @function
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IMovieService movieService;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CataLogRepository cataLogRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MovieTagRepository movieTagRepository;
    @RequestMapping
    public ModelAndView listMovie(Model model){
        ModelAndView modelAndView=new ModelAndView();
        List<Movie> movieList=movieService.listMovie();
         model.addAttribute("movieList",movieList);
         modelAndView.setViewName("admin/listMovie");
         modelAndView.addObject("model",model);
         return  modelAndView;
    }

    @RequestMapping(value = "/addMovie",method = RequestMethod.GET)
    public String addMovie(@RequestParam(required = false,defaultValue ="") Integer movieId,Model model){
        Movie movie=null;
        /*
        * 是否为修改
        * */
        if(movieId!=null) {
            movie = movieRepository.findOne(movieId);
        }
        /*
        *不是的话添加movie值
        * */
        if(movie==null)
            movie=new Movie();
        model.addAttribute(movie);
        model.addAttribute("countryList",countryRepository.findAll());
        model.addAttribute("catalogList",cataLogRepository.findAll());
        System.out.println("1111");
        return "admin/addMovie";
    }

    @RequestMapping(value = "/addMovie",method = RequestMethod.POST)
    @Transactional
    public  String saveMovie(Movie movie, Model model){
        if(movie.getId()!=null){

                Movie oldMovie=movieRepository.findOne(movie.getId());
                String oldTags=oldMovie.getTags();
                movieTagRepository.deleteAllByMovieId(movie.getId());
                String[] oldtags=oldTags.split(",");
                for(String singleOldTag:oldtags){
                    Tag tag=tagRepository.findByName(singleOldTag);
                    tag.setCount(tag.getCount()-1);
                    tagRepository.save(tag);
                }

        }

        Movie movie1=movieService.addMovie(movie);
        int movieId=movie1.getId();
        String tagString=movie.getTags();
        String[] tags=tagString.split(",");
        Tag tag=null;
        for(String tagName:tags){
           tag=tagRepository.findByName(tagName);
            if(tag==null){
                tag=new Tag();
                tag.setName(tagName);
                tag.setCount(1);
                Tag tag2=tagRepository.save(tag);
                int tagid=tag2.getId();
                MoiveTag movieTag=new MoiveTag();
                movieTag.setTagId(tagid);
                movieTag.setMovieId(movieId);
                movieTagRepository.save(movieTag);
            }
            else {
                tag.setCount(tag.getCount()+1);
                int tagid=tag.getId();
                MoiveTag movieTag=new MoiveTag();
                movieTag.setTagId(tagid);
                movieTag.setMovieId(movieId);
                movieTagRepository.save(movieTag);

            }
            System.out.println(tagName);

        }
        return  "redirect:/admin";

    }
    @RequestMapping(value = "/deleteMovie")
    @Transactional
    public String deleteMovie(Integer movieId){
        if(movieId!=null){

            Movie oldMovie=movieRepository.findOne(movieId);
            String oldTags=oldMovie.getTags();
            movieTagRepository.deleteAllByMovieId(movieId);
            String[] oldtags=oldTags.split(",");
            for(String singleOldTag:oldtags){
                Tag tag=tagRepository.findByName(singleOldTag);
                tag.setCount(tag.getCount()-1);
                tagRepository.save(tag);
            }

            movieService.deleteMovie(movieId);
        }
        return "redirect:/admin";
    }

    @RequestMapping("/detailMovie")
    public ModelAndView showMovie( Integer movieId, Model model){
        Movie movie=movieRepository.findOne(movieId);
        model.addAttribute("movie",movie);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName( "admin/movieDetail");
        modelAndView.addObject("model",model);
        return modelAndView;
    }

}
