package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/inventory")
@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PutMapping
    public void updateInventory(
            @RequestParam(value = "product") final String product,
            @RequestParam(value = "quantity") final Long quantity) {
        inventoryService.update(product, quantity);
    }

}
