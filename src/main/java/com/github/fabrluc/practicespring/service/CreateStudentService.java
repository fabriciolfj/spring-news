package com.github.fabrluc.practicespring.service;

import com.github.fabrluc.practicespring.entities.Address;
import com.github.fabrluc.practicespring.entities.StudentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.IntStream;

@Service
@Slf4j
public record CreateStudentService(StudentService studentService) implements CommandLineRunner {

    public void save() {
        IntStream.range(0, 500)
                .forEach(this::execute);
    }

    private void execute(int value) {
        var student = new StudentEntity(null, String.valueOf(value), new Address("1011111", "Serrana"));
        try {
            studentService.createStudent(student);
            log.info("student salvo com sucesso {}", Optional.of(value));
        } catch (Exception e) {
            log.info("falha ao salvar o student {}", Optional.of(value));
        }
    }

    @Override
    public void run(String... args) throws Exception {
        save();
    }
}
