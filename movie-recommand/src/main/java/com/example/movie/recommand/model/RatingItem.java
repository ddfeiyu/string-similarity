package com.example.movie.recommand.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dd
 * @Date 2022/7/26-11:15
 * @function
 */
@Data
@Entity
public class RatingItem {
    @Id @Column(name="ratingItem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer rating;
    private String comment;
    private Date commentDate;
    @OneToOne
    @JoinColumn(name = "movie_id")
    private  Movie movie;
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
