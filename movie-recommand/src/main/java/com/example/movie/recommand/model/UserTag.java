package com.example.movie.recommand.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author dd
 * @Date 2022/7/31-21:48
 * @function
 */
@Entity
@Data
public class UserTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private  String name;
    private  Integer userId;
}
