package com.example.movie.recommand.respository;

import com.example.movie.recommand.model.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dd
 * @Date 2022/7/2-12:17
 * @function
 */
public interface UserTagRespository extends JpaRepository<UserTag,Integer> {
    public List<UserTag> findByUserId(Integer userid);

}
