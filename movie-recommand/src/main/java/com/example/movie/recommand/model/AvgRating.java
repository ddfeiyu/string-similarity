package com.example.movie.recommand.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dd
 * @Date 2022/7/2-17:18
 * @function
 */
@Entity
@Data
@Table(name ="avgrating")
public class AvgRating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public  Integer id;
    @Column(name ="usrid")
    public  Integer userId;
    @Column(name = "avgRating")
    public  Integer avgRating;
}
