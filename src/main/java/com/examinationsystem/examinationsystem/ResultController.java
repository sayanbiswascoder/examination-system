package com.examinationsystem.examinationsystem;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.examinationsystem.examinationsystem.model.Result;
import com.examinationsystem.examinationsystem.model.Student;
import com.examinationsystem.examinationsystem.service.ResultService;
import com.examinationsystem.examinationsystem.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/result")
public class ResultController {
    @Autowired
    private ResultService resultService;
    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public String resultPage(Model model, HttpServletRequest request) {
        String regno = request.getParameter("regno");
        String semesterParam = request.getParameter("semester");
        int semester = (semesterParam != null) ? Integer.parseInt(semesterParam) : -1; // Default to -1 if null
        if(regno == null && semesterParam == null) {
            return  "resultForm";
        }

        Student student = studentService.getStudentById(regno);

        List<Result> results = resultService.getResultsByStudentAndSemester(student, semester);
        int totalMark = results.stream().mapToInt(Result::getTotal_marks).sum();
        int obtainedMark = results.stream().mapToInt(Result::getObtained_marks).sum();
        double percentage = (totalMark > 0) ? ((double) obtainedMark / totalMark) * 100 : 0;
        model.addAttribute("student", student);
        model.addAttribute("results", results);
        model.addAttribute("totalMark", totalMark);
        model.addAttribute("obtainedMark", obtainedMark);
        model.addAttribute("percentage", percentage);

        return "result";
    }
}
