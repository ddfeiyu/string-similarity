package com.example.movie.recommand.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dd
 * @Date 2022/7/15-17:35
 * @function
 */
@Data
@Entity
public class User {
    @Id @Column(name ="user_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  Integer id;
    private  String  name;
   // private  String email;
    private  Boolean firstLogin=true;
    private  String password;
}
