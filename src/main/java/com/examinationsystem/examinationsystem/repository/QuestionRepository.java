package com.examinationsystem.examinationsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examinationsystem.examinationsystem.model.Exam;
import com.examinationsystem.examinationsystem.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Custom query methods can be added here if needed
    List<Question> findByExam(Exam exam);
    Question findByExamAndNumber(Exam exam, int questionNo);
}
