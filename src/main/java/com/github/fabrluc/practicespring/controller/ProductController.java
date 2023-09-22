package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.entities.Product;
import com.github.fabrluc.practicespring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/api/v1")
public class ProductController {

    private final ProductRepository productRepository;

    private final static Random RANDOM = new Random();

    @PostMapping
    @Transactional
    public Mono<Void> populate() {
        return Flux.range(0, 1000)
                .map(c -> new Product(null, "Test " + c, BigDecimal.valueOf(RANDOM.nextDouble())))
                .flatMap(productRepository::save)
                .then();
    }

    @GetMapping
    public Mono<Page<Product>> findAllProducts(final Pageable pageable) {
        return this.productRepository.findAllBy(pageable)
                .collectList()
                .zipWith(productRepository.count())
                .map(c -> new PageImpl<>(c.getT1(), pageable, c.getT2()));
    }
}
