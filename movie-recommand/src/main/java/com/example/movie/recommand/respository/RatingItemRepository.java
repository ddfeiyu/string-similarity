package com.example.movie.recommand.respository;

import com.example.movie.recommand.model.Movie;
import com.example.movie.recommand.model.RatingItem;
import com.example.movie.recommand.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author dd
 * @Date 2022/7/26-16:15
 * @function
 */
public interface RatingItemRepository extends JpaRepository<RatingItem,Integer> {
    Page<RatingItem> findByMovie(Movie movie, Pageable pageable);
    RatingItem findByUser(User user);
    @Query(value = "select user_id,avg(rating) from rating_item group by user_id order by user_id",nativeQuery = true)
    Object[] getAvgeRating();

}
