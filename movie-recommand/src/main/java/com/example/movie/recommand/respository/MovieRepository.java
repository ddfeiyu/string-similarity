package com.example.movie.recommand.respository;

import com.example.movie.recommand.model.CataLog;
import com.example.movie.recommand.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dd
 * @Date 2022/7/8-18:43
 * @function
 */
public interface MovieRepository extends JpaRepository<Movie,Integer>{
        public Page<Movie> findByCataLog(CataLog cataLog, Pageable pageable);
        public  List<Movie> findByCataLog(CataLog cataLog);
        

}
