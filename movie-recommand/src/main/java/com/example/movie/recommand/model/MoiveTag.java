package com.example.movie.recommand.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dd
 * @Date 2022/7/31-21:39
 * @function
 */
@Data
@Entity
@Table(name = "movie_tags")
//词表用于保存movietag和tag表之间的实体关系，手工进行维护不交给Hibernate框架维护。
public class MoiveTag{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private Integer movieId;//存储movie_id
    private Integer tagId;//存储tag_id
}
