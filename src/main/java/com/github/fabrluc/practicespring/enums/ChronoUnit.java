package com.github.fabrluc.practicespring.enums;

import lombok.AllArgsConstructor;

import java.time.Duration;

@AllArgsConstructor
public enum ChronoUnit {
    SECONDS("Seconds", Duration.ofSeconds(1)),
    MINUTES("Minutes", Duration.ofSeconds(60)),
    HOURS("Hours", Duration.ofSeconds(3600)),
    DAYS("Days", Duration.ofSeconds(86400));

    private String describe;
    private Duration duration;
}