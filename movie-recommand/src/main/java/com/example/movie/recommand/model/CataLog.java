package com.example.movie.recommand.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dd
 * @Date 2022/7/15-14:26
 * @function
 */
@Data
@Entity
public class CataLog {
    @Id  @Column(name = "catalog_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private  String name;
}
