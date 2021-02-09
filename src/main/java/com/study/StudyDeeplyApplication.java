package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StudyDeeplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyDeeplyApplication.class, args);
	}

}
