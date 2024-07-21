package com.github.fabrluc.practicespring.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FileContentSearchService {

    private final FileService fileService;

    private final Logger log = LoggerFactory.getLogger(FileContentSearchService.class);

    public Mono<Boolean> nonBlockingSearch(String fileName, String searchTerm) {
        return fileService.getFileContentAsString(fileName)
                .doOnNext(content -> log.info("1. NonBlockingSearch"))
                .map(content -> content.contains(searchTerm))
                .doOnNext(content -> log.info("2. NonBlockingSearch"));
    }

    public Mono<Boolean> blockingSearch(String fileName, String searchTerm) {
        String fileContent = fileService
                .getFileContentAsString(fileName)
                .doOnNext(content -> log.info("1. BlockingSearch"))
                .block();

        boolean isSearchTermPresent = fileContent.contains(searchTerm);

        return Mono.just(isSearchTermPresent);
    }
}