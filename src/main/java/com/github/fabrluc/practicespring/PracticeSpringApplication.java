package com.github.fabrluc.practicespring;

import com.github.fabrluc.practicespring.entities.Product;
import com.github.fabrluc.practicespring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Flushable;
import java.math.BigDecimal;
import java.util.Random;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "com.github.fabrluc.practicespring.repository")
public class PracticeSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeSpringApplication.class, args);
	}

	private final static Random RANDOM = new Random(1000);

	@Autowired
	private ProductRepository productRepository;
}
