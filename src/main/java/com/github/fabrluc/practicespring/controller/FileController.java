package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.service.FileContentSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileContentSearchService fileContentSearchService;

    @GetMapping(value = "/{name}/blocking-search")
    Mono<Boolean> blockingSearch(@PathVariable("name") String fileName, @RequestParam String term) {
        return fileContentSearchService.blockingSearch(fileName, term);
    }
}
