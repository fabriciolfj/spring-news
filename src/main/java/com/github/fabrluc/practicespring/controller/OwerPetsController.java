package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.entities.Owner;
import com.github.fabrluc.practicespring.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwerPetsController {

    private final OwnerService ownerService;

    @GetMapping
    public Owner getOwnerService() {
        return ownerService.findById(1L);
    }
}
