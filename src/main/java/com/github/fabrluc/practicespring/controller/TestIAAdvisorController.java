package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/advisors")
public class TestIAAdvisorController {

    private final WeatherService weatherService;

    @GetMapping("/{location}")
    public void getAnswer(@PathVariable("location") final String message) {
        weatherService.execute(message);
    }
}
