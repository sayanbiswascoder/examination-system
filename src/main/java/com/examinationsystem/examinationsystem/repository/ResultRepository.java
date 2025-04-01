package com.examinationsystem.examinationsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examinationsystem.examinationsystem.model.Result;
import com.examinationsystem.examinationsystem.model.Student;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    // Custom query methods can be added here if needed
    List<Result> findByStudentAndSemester(Student student, int semester);
}
