package com.example.movie.recommand.controller;

import com.example.movie.recommand.model.*;
import com.example.movie.recommand.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dd
 * @Date 2022/7/24-19:04
 * @function
 */
@Controller
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private CataLogRepository cataLogRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RatingItemRepository ratingItemRepository;

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserTagRespository userTagRespository;
    @RequestMapping("movieByCataLog/{cataLogId}")
    public ModelAndView getMovieListByCataLog(@RequestParam(defaultValue = "0", required = false) Integer page,
                                          @RequestParam(defaultValue = "2", required = false) Integer size,
                                          @PathVariable("cataLogId") Integer cataLogId,
                                          @RequestParam(defaultValue = "false", required = false) Boolean asyn) {
        ModelAndView modelAndView = new ModelAndView();
        CataLog cataLog = cataLogRepository.findOne(cataLogId);
        if (asyn) {
            System.out.println("asyn=True11");
            Pageable pageable = new PageRequest(page, size);
            Page<Movie> pageBean = movieRepository.findByCataLog(cataLog, pageable);
            modelAndView.addObject("cataLogPageBean", pageBean);
            modelAndView.setViewName("index::#CommonMovieInform");
            System.out.println("页数" + pageBean.getTotalPages() + "当前页数" + (pageBean.getNumber() + 1));
        }

        // mpdelAndView=new MovieController();

        return modelAndView;
    }

    @RequestMapping("/movieDetail")
    public ModelAndView getMovieDetail(@RequestParam(defaultValue = "0", required = false) Integer page,
                                       @RequestParam(defaultValue = "2", required = false) Integer size,
                                       @RequestParam(defaultValue = "false") Boolean asyn, HttpSession session,
                                       Integer movieId) {
        ModelAndView modelAndView = new ModelAndView();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            RatingItem ratingItemOfUser = ratingItemRepository.findByUser(user);
            modelAndView.addObject("ratingItemOfUser", ratingItemOfUser);
        }

        Movie movie = movieRepository.findOne(movieId);
        Pageable pageable = new PageRequest(page, size);
        Page<RatingItem> pageBean = null;
        if (movie != null) {
            pageBean = ratingItemRepository.findByMovie(movie, pageable);
        }
        modelAndView.addObject("movie", movie);
        modelAndView.addObject("ratingItemPageBean", pageBean);
        modelAndView.setViewName("/front/movie/movieDetail");
        if (asyn) {
            modelAndView.setViewName("/front/movie/movieDetail::#ratingListContent");
        }
        System.out.println("总页数" + pageBean.getTotalPages());
        System.out.println("当前第" + (pageBean.getNumber() + 1));

        return modelAndView;
    }


    @RequestMapping("movieByUserTag")
    public  ModelAndView getMovieListByUserTag(HttpSession session){
        ModelAndView modelAndView=null;
        Integer userId=null;
        Tag tag=null;
        List<Integer> movieIdList=new LinkedList<>();
        List<UserTag> userTagList=userTagRespository.findByUserId(userId);
        for(UserTag userTag:userTagList){
            String tagName=userTag.getName();
            tag=tagRepository.findByName(tagName);
            Integer tagId=tag.getId();

        }
        return  modelAndView;
    }
}
