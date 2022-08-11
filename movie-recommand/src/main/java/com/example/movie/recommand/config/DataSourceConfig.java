package com.example.movie.recommand.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @author dd
 * @Date 2022/7/6-20:45
 * @function
 */
//#database start
//        spring.datasource.url=jdbc:mysql://127.0.0.1:3306/movie_rc?useUnicode=true&characterEncoding=UTF-8
//        spring.datasource.username=root
//        spring.datasource.password=123456
//        spring.datasource.driver-class-name=com.mysql.jdbc.Driver

@Configuration
public class DataSourceConfig {
    @Bean(name = "datasource")
    public DataSource dataSource(Environment env){
        HikariDataSource ds=new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("spring.datasource.url"));
        ds.setUsername(env.getProperty("spring.datasource.username"));
        ds.setPassword(env.getProperty("spring.datasource.password"));
        ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        System.out.println("hellpzaza");
        return  ds;
    }

}
