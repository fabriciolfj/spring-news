package com.github.fabrluc.practicespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PracticeSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeSpringApplication.class, args);
	}

}
