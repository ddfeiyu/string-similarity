package com.example.movie.recommand.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dd
 * @Date 2022/7/2-17:10
 * @function
 */
@Entity
@Data
@Table(name = "recom")
public class RecomItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private   Integer id;
    @Column(name = "uid")
    private   Integer userId;
    @Column(name="mid")
    private Integer moiveid;
    @Column(name = "reasonids")
    private String recommendReasonIds;
    private  Double recdg;
}
