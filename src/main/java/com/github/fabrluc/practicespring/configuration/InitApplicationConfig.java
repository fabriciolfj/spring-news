package com.github.fabrluc.practicespring.configuration;

import com.github.fabrluc.practicespring.service.JsonPlaceholderService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitApplicationConfig {

    private final JsonPlaceholderService jsonPlaceholderService;
    //private final ObservationRegistry registry;

    @Bean
    @Observed(name = "posts.load-all-posts", contextualName = "post-service.find-all")
    CommandLineRunner commandLineRunner() {
        return args -> {
            var result = jsonPlaceholderService.findAll();
            log.info("posts: {}", result.size());
            /*Observation.createNotStarted("posts.load-all-posts", registry)
                    .lowCardinalityKeyValue("author", "Fabricio Jacob")
                    .contextualName("post-service.find-all")
                    .observe(() -> {
                        var result = jsonPlaceholderService.findAll();
                        log.info("posts: {}", result.size());
        });*/
        };
    }
}
