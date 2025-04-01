package com.examinationsystem.examinationsystem.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Exam {
    @Id
    private int subcode;
    private String name;
    private int semester;
    private String department;
    private int duration; // in minite
    @Column(nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    private int totalmarks;

    public Exam() {

    }

    public Exam(int sCode, String n, int sem, String dept, int dur, LocalDate eDate, LocalTime eTime, int tMarks) {
        this.subcode = sCode;
        this.name = n;
        this.semester = sem;
        this.department = dept;
        this.duration = dur;
        this.date = eDate;
        this.time = eTime;
        this.totalmarks = tMarks;
    }

    // geter and seter
    public int getSubcode() {
        return subcode;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String dep) {
        this.department = dep;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getTotalmarks() {
        return totalmarks;
    }

    public void setTotalmarks(int totalmarks) {
        this.totalmarks = totalmarks;
    }
}