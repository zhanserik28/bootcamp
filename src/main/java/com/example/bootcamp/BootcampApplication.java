package com.example.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class BootcampApplication {
	public static final long MAX_UPLOAD_SIZE = 10485760;

	public static void main(String[] args) {
		SpringApplication.run(BootcampApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public MultipartResolver multipartResolver() {
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//		multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
//		return multipartResolver;
//	}

}
