package com.github.fabrluc.practicespring.service;

import com.github.fabrluc.practicespring.advisors.MyLogAdvisor;
import com.github.fabrluc.practicespring.tools.MyTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    private final ChatClient.Builder chatClientBuilder;
    private final MyTools myTools;
    private final MyLogAdvisor logAdvisor;

    public void execute(final String message) {
        var chatClient = chatClientBuilder
                .defaultAdvisors(logAdvisor)
                .build();

        var answer = chatClient
                .prompt(message)
                .tools(myTools)
                .call()
                .content();

        log.info("result {}", answer);
    }
}
