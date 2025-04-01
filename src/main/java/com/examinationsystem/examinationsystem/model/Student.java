package com.examinationsystem.examinationsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    private String regno;
    private String name;
    private int semester;
    private String department;

    public Student() {

    }

    public Student(String regNo, String n, int sem, String dept) {
        this.regno = regNo;
        this.name = n;
        this.semester = sem;
        this.department = dept;
    }
    // geter and seter
    public void setRegno(String regno) {
        this.regno = regno;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSem(int sem) {
        this.semester = sem;
    }
    public void setDepartment(String dept) {
        this.department = dept;
    }
    public String getRegno() {
        return regno;
    }

    public String getName() {
        return name;
    }

    public int getSemester() {
        return semester;
    }

    public String getDepartment() {
        return department;
    }
}