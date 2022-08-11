package com.example.movie.recommand.helper;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author dd
 * @Date 2022/7/24-13:53
 * @function
 */
@Component
@Data
public class DataHelper {
    private  boolean status;
    private  String messager;
    private  Object data;

}
