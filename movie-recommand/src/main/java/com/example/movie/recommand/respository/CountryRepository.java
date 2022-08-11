package com.example.movie.recommand.respository;

import com.example.movie.recommand.model.Country;
import org.springframework.data.repository.CrudRepository;

/**
 * @author dd
 * @Date 2022/7/15-13:36
 * @function
 */
public interface CountryRepository  extends CrudRepository<Country,Integer> {
}
