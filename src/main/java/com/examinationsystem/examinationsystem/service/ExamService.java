package com.examinationsystem.examinationsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examinationsystem.examinationsystem.model.Exam;
import com.examinationsystem.examinationsystem.repository.ExamRepository;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamBySubcode(int subcode) {
        return examRepository.findBySubcode(subcode).orElse(null);
    }

    public List<Exam> findExamsByDepartmentAndSemester(String department, int semester) {
        return examRepository.findByDepartmentAndSemester(department, semester);
    }

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public void deleteExam(int subcode) {
        examRepository.deleteBySubcode(subcode);
    }
}
