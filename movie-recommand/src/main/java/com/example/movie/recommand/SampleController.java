package com.example.movie.recommand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
//@ComponentScan
//@EnableAutoConfiguration
public class SampleController {

	@RequestMapping("/index")
	public String home() {
		return "hello";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleController.class, args);
	}
}