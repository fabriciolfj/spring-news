package com.github.fabrluc.practicespring.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnThreading;
import org.springframework.boot.autoconfigure.thread.Threading;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrentConfig {

    @Bean
    @ConditionalOnThreading(Threading.PLATFORM)
    public ThreadingType plataformBean() {
        return ThreadingType.PLATAFORM;
    }

    @Bean
    @ConditionalOnThreading(Threading.VIRTUAL)
    public ThreadingType virtualBean() {
        return ThreadingType.VIRTUAL;
    }
}


enum ThreadingType {
    PLATAFORM, VIRTUAL
}