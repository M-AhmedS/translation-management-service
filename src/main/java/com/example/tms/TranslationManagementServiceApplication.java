package com.example.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TranslationManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslationManagementServiceApplication.class, args);
	}

}
