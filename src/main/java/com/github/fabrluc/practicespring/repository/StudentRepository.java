package com.github.fabrluc.practicespring.repository;

import com.github.fabrluc.practicespring.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    @Query(value = "SELECT * FROM student WHERE address->>'postCode' = :postCode", nativeQuery = true)
    List<StudentEntity> findByAddressPostCode(@Param("postCode") final String postCode);
}
