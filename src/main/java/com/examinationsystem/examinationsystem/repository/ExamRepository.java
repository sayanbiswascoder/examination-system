package com.examinationsystem.examinationsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examinationsystem.examinationsystem.model.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findBySubcode(int subcode);
    List<Exam> findByDepartmentAndSemester(String department, int semester);
    void deleteBySubcode(int subcode);
}
