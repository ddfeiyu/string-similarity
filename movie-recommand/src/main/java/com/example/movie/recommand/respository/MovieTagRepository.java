package com.example.movie.recommand.respository;

import com.example.movie.recommand.model.MoiveTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dd
 * @Date 2022/7/1-17:25
 * @function
 */
public interface MovieTagRepository extends JpaRepository<MoiveTag,Integer> {
    public List<MoiveTag> findByMovieId(Integer movieId);
    public MoiveTag findByMovieIdAndTagId(Integer movieId,Integer tagId);
    public void deleteAllByMovieId(Integer movieId);
    public Page<MoiveTag> findByTagId(Integer tagId, Pageable pageable);
}
