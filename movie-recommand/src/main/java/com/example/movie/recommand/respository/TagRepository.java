package com.example.movie.recommand.respository;

import com.example.movie.recommand.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dd
 * @Date 2022/7/31-21:54
 * @function
 */
public interface TagRepository extends JpaRepository<Tag,Integer>{
    public Tag findByName(String name);

}
