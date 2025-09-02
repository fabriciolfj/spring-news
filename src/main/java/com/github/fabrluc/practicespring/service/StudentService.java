package com.github.fabrluc.practicespring.service;

import com.github.fabrluc.practicespring.entities.StudentEntity;
import com.github.fabrluc.practicespring.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final Random random = new Random();

    @Transactional(propagation = Propagation.REQUIRED)
    public void createStudent(final StudentEntity entity) {
        if (random.nextInt() % 2 == 0) {
            throw new RuntimeException();
        }

        studentRepository.save(entity);
    }

}
