package com.github.fabrluc.practicespring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@RestController
public class MessagesController {

    private final RestClient restClient;

    public MessagesController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> messages() {
        Message[] messages = this.restClient.get()
                .uri("http://localhost:8090/messages")
                .attributes(clientRegistrationId("messaging-client"))
                .retrieve()
                .body(Message[].class);

        return ResponseEntity.ok(Arrays.asList(messages));
    }

    public record Message(String message) {
    }

}