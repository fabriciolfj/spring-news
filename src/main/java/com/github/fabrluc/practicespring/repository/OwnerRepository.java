package com.github.fabrluc.practicespring.repository;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.github.fabrluc.practicespring.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findById(Long id, EntityGraph entityGraph);
}
