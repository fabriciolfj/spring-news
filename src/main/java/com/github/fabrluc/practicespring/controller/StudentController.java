package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.entities.StudentEntity;
import com.github.fabrluc.practicespring.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository  repository;

    @PostMapping
    public void create(@RequestBody final StudentEntity studentEntity) {
        repository.save(studentEntity);
    }
}
