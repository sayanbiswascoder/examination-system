package com.examinationsystem.examinationsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "regno", referencedColumnName = "regno")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subcode", referencedColumnName = "subcode") 
    private Exam exam;

    private int semester;

    private int total_marks;
    private int obtained_marks;

    public Result() {
    }

    public Result(Student student, Exam exam, int sem, int total_marks, int obtained_marks) {
        this.student = student;
        this.exam = exam;
        this.semester = sem;
        this.total_marks = total_marks;
        this.obtained_marks = obtained_marks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(int total_marks) {
        this.total_marks = total_marks;
    }

    public int getObtained_marks() {
        return obtained_marks;
    }

    public void setObtained_marks(int obtained_marks) {
        this.obtained_marks = obtained_marks;
    }
}
