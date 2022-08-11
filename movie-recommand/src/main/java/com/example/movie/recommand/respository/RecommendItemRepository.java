package com.example.movie.recommand.respository;

import com.example.movie.recommand.model.RecomItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dd
 * @Date 2022/7/2-17:32
 * @function
 */

public interface RecommendItemRepository extends JpaRepository<RecomItem,Integer>{
   // public  Page<RecomItem>  findByUserIdAndOrderByRecdgDesc(Integer userId, Pageable pageable);
}
