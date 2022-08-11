package com.example.movie.recommand.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dd
 * @Date 2022/7/15-13:23
 * @function
 */
@Data
@Entity
public class Country {
    @Id @Column(name = "country_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private  String  name;
}
