package com.github.fabrluc.practicespring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class InventoryService {

    private final ConcurrentHashMap<String, Long> DB = new ConcurrentHashMap<>();

    public synchronized void update(final String product, final Long quantity) {
        var result = DB.compute(product, (k, value) -> quantity + ((value == null) ? 0: value));

        log.info("quantity {}", result);
    }
}
