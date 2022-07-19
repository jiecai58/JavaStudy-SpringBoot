package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author leo
 */
@EnableAsync
@SpringBootApplication
@EnableCaching
public class StudyDeeplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyDeeplyApplication.class, args);
	}

}
