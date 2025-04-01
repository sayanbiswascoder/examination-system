package com.examinationsystem.examinationsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examinationsystem.examinationsystem.model.Result;
import com.examinationsystem.examinationsystem.model.Student;
import com.examinationsystem.examinationsystem.repository.ResultRepository;

@Service
public class ResultService {
    @Autowired
    private ResultRepository resultRepository;

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Result getResultById(Long id) {
        return resultRepository.findById(id).orElse(null);
    }

    public List<Result> getResultsByStudentAndSemester(Student student, int semester) {
        return resultRepository.findByStudentAndSemester(student, semester);
    }

    
    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }
}
