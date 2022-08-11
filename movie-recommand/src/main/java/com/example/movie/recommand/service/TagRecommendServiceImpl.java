package com.example.movie.recommand.service;

import com.example.movie.recommand.model.MoiveTag;
import com.example.movie.recommand.model.Movie;
import com.example.movie.recommand.model.UserTag;
import com.example.movie.recommand.respository.MovieRepository;
import com.example.movie.recommand.respository.MovieTagRepository;
import com.example.movie.recommand.respository.TagRepository;
import com.example.movie.recommand.respository.UserTagRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dd
 * @Date 2022/7/2-14:36
 * @function
 */
@Service
public class TagRecommendServiceImpl implements ITagRecommendService
{
    @Autowired
    private MovieTagRepository movieTagRepository;
    @Autowired
    private UserTagRespository userTagRespository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TagRepository tagRepository;
    @Override
    public List<Movie> recommend(Integer userId){ ;
        List<UserTag> userTagList=null;
        List<Movie> recommendMovieListByTag=new LinkedList<>();
        if(userId!=null){
            //找到用户的tag列表
            userTagList= userTagRespository.findByUserId(userId);
        }

        //随机选择一个标签主题
        String chooseName;
        Double random=Math.random();
        Integer chooseIndex=(int)( (random*userTagList.size()-1));
        System.out.println("chooseIndex"+chooseIndex);
        chooseName=userTagList.get(chooseIndex).getName();
 //       UserTag chooseTag=userTagList.get()
        System.out.println("chosseName"+chooseName);
        //给用户做标签推荐
        System.out.println(userTagList);
        Pageable pageable=new PageRequest(0,3);
        Integer tagId=tagRepository.findByName(chooseName).getId();
        Page<MoiveTag> movieTagPage=movieTagRepository.findByTagId(tagId,pageable);
        List<MoiveTag> movieTagListByPage= movieTagPage.getContent();
        for(MoiveTag movieTag: movieTagListByPage ){
            Movie movie=movieRepository.findOne(movieTag.getMovieId());
            recommendMovieListByTag.add(movie);
            System.out.println(movie);
        }
        return recommendMovieListByTag;
    }
}
