package com.examinationsystem.examinationsystem;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.examinationsystem.examinationsystem.model.Exam;
import com.examinationsystem.examinationsystem.service.ExamService;

@Controller
public class HomeController {
    @Autowired
    private ExamService examService;

    @GetMapping("/")
    public String sayHello(Model model) {
        List<String> upcomingExams = examService.getAllExams().stream()
            .filter(exam -> {
                LocalDateTime examDateTime = LocalDateTime.of(exam.getDate(), exam.getTime());
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(now, examDateTime);
                return !duration.isNegative() && duration.toHours() <= 12;
            })
            .map(Exam::getName)
            .collect(Collectors.toList());

        model.addAttribute("upcomingExams", upcomingExams);
        return "index";
    }
}