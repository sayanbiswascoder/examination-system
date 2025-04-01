package com.examinationsystem.examinationsystem.repository;

import com.examinationsystem.examinationsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRegno(String regno);
    void deleteByRegno(String regno);
}
