package com.example.movie.recommand.recommend.respository;



import java.util.List;
import java.util.Map;

/**
 * @author dd
 * @Date 2022/7/28-13:42
 * @function
 */
public interface AvgRatingRespository {
    //根据用户的id获取其平均分
    public Double findByUserId(Integer userId);
    public void deleteAll();
    public Map<Integer,Double> findAvgRatingMapByUserIdList(List<Integer> userIdlist);
    public void generateAll();
}
