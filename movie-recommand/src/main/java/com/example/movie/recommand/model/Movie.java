package com.example.movie.recommand.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dd
 * @Date 2022/7/6-19:46
 * @function
 */
@Data
@Entity
public class Movie {
    @Id @Column(name = "movie_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private  Integer id;
    private  String title;
    @Column(columnDefinition = "text")
    private  String detail;
    @Column(columnDefinition = "text")
    private  String imgUrl;
    @Column(columnDefinition = "text")
    private String videoUrl;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdate;
    @Column(length = 10)
    private String releaseDate;
    @OneToOne
    @JoinColumn(name="country_id",referencedColumnName="country_id")
    private Country country=new Country();
    @OneToOne
    @JoinColumn(name = "catalog_id",referencedColumnName = "catalog_id")
    private  CataLog cataLog=new CataLog();
    private  String tags;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="movie_id",referencedColumnName ="movie_id")
    private List<RatingItem> ratingItemList=new LinkedList<RatingItem>();
//    @ManyToMany
//    @JoinTable(name = "movie_actor",
//    joinColumns = @JoinColumn(name="movie_id" ,referencedColumnName = "movie_id"),
//    inverseJoinColumns = @JoinColumn(name ="actor_id",referencedColumnName = "actor_id"))
//    private Set<Actor> ActorSet=new HashSet<>();
}
