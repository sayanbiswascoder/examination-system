package com.examinationsystem.examinationsystem.service;

import com.examinationsystem.examinationsystem.model.Student;
import com.examinationsystem.examinationsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String regno) {
        return studentRepository.findByRegno(regno).orElse(null);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(String regno) {
        studentRepository.deleteByRegno(regno);
    }
}
