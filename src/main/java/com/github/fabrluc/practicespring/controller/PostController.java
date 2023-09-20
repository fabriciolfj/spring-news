package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.dto.Post;
import com.github.fabrluc.practicespring.service.JsonPlaceholderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final JsonPlaceholderService jsonPlaceholderService;

    @GetMapping
    public List<Post> findAll() {
        return jsonPlaceholderService.findAll();
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable("id") final Integer id) {
        return jsonPlaceholderService.findById(id);
    }
}
