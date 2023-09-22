package com.github.fabrluc.practicespring.repository;

import com.github.fabrluc.practicespring.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProductRepository extends ReactiveSortingRepository<Product, UUID>, ReactiveCrudRepository<Product, UUID> {

    Flux<Product> findAllBy(Pageable pageable);

}
