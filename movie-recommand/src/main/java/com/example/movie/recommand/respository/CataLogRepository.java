package com.example.movie.recommand.respository;

import com.example.movie.recommand.model.CataLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dd
 * @Date 2022/7/15-14:43
 * @function
 */
public interface CataLogRepository extends JpaRepository<CataLog,Integer> {
}
