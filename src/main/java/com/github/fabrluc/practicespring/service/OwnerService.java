package com.github.fabrluc.practicespring.service;

import com.cosium.spring.data.jpa.entity.graph.domain2.NamedEntityGraph;
import com.github.fabrluc.practicespring.entities.Owner;
import com.github.fabrluc.practicespring.repository.OwnerRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;

@Server
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public Owner findById(final Long id) {
        return ownerRepository.findById(id,
                        NamedEntityGraph.fetching("owner-with-pets"))
                .orElseThrow();
    }
}
