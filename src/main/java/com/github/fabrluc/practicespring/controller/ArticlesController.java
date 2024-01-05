package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.entities.Articles;
import com.github.fabrluc.practicespring.repository.ArticlesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticlesRepository repository;

    @PostMapping
    public void save(@RequestBody final Articles articles) {
        repository.saveWithPositionalSpELArgumentsWithEmptyCheck(articles.getId(), articles.getTitle(), articles.getContent(), articles.getLanguage());
    }
}
