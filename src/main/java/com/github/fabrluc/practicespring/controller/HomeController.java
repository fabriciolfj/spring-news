package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.configuration.WeekDaysHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @Autowired
    private WeekDaysHolder holder;

    @GetMapping("")
    public String home() {
        log.info(holder.getFriday().name());
        return "Hello world";
    }
}
