package com.examinationsystem.examinationsystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam")
public class ExamController {

    @GetMapping("")
    public String examPage(Model model) {
        return "exam";
    }


    @GetMapping("/student-details")
    public String studentDetails(Model model) {
        return "studentDetailsExam";
    }

    @GetMapping("/greet")
    public String greet(Model model) {
        return "greet";
    }


}