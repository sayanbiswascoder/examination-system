package com.examinationsystem.examinationsystem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examinationsystem.examinationsystem.model.Exam;
import com.examinationsystem.examinationsystem.model.Question;
import com.examinationsystem.examinationsystem.model.Result;
import com.examinationsystem.examinationsystem.model.Student;
import com.examinationsystem.examinationsystem.service.ExamService;
import com.examinationsystem.examinationsystem.service.QuestionService;
import com.examinationsystem.examinationsystem.service.ResultService;
import com.examinationsystem.examinationsystem.service.StudentService;



@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    private StudentService studentServide;
    @Autowired
    private ExamService examService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ResultService resultService;

    @PostMapping("/verify-student")
    public ResponseEntity<?> verifyStudentAndExam(@RequestBody Student student) {
        // Verify student details
        Student dbStudent = studentServide.getStudentById(student.getRegno());
        
        if (dbStudent == null) {
            return ResponseEntity.status(404).body("Student not found");
        }
        
        // Check if student details match
        if (!dbStudent.getName().toLowerCase().equals(student.getName().toLowerCase()) ||
            !dbStudent.getDepartment().equals(student.getDepartment()) ||
            dbStudent.getSemester() != student.getSemester()) {
            return ResponseEntity.status(400).body("Student details do not match");
        }

        // Get exams for student's department and semester
        List<Exam> exams = examService.findExamsByDepartmentAndSemester(student.getDepartment(), student.getSemester());
        
        if (exams.isEmpty()) {
            return ResponseEntity.status(404).body("No exams found for given department and semester");
        }

        // Check for exams starting within next 15 minutes
        LocalTime currentTime = LocalTime.now();
        LocalDate today = LocalDate.now();
        
        for (Exam exam : exams) {
            if (exam.getDate().equals(today)) {
                LocalTime examTime = exam.getTime();
                long minutesDiff = ChronoUnit.MINUTES.between(currentTime, examTime);
                
                if (minutesDiff >= -exam.getDuration() && minutesDiff <= 15) {
                    return ResponseEntity.ok()
                        .body(Map.of(
                            "subcode", exam.getSubcode(),
                            "examName", exam.getName(),
                            "startTime", exam.getTime()
                        ));
                }
            }
        }

        return ResponseEntity.status(404).body("No exam scheduled within next 15 minutes");
    }

    @PostMapping("/start-exam")
    public ResponseEntity<?> startExam(@RequestBody Map<String, String> request) {
        String regno = request.get("regno");
        String subcode = request.get("subcode");

        // Verify student exists
        Student student = studentServide.getStudentById(regno);
        if (student == null) {
            return ResponseEntity.status(404).body("Student not found");
        }

        // Verify exam exists and is currently active
        Exam exam = examService.getExamBySubcode(Integer.parseInt(subcode));

        if (exam == null) {
            return ResponseEntity.status(404).body("No active exam found for given subject code");
        }

        // Verify student's department and semester match exam requirements
        if (!student.getDepartment().equals(exam.getDepartment()) || 
            student.getSemester() != exam.getSemester()) {
            return ResponseEntity.status(403)
                .body("Student's department/semester does not match exam requirements");
        }

        // Create response with exam and student details
        Map<String, Object> examDetails = new HashMap<>();
        examDetails.put("regno", student.getRegno());
        examDetails.put("subcode", exam.getSubcode());

        // Redirect to exam page with details
        return ResponseEntity.status(302)
            .body(examDetails);
    }


    @PostMapping("/questions")
    public ResponseEntity<?> getQuestions(@RequestBody Map<String, String> request) {
        String regno = request.get("regno");
        String subcode = request.get("subcode");

        // Verify student exists
        Student student = studentServide.getStudentById(regno);
        if (student == null) {
            return ResponseEntity.status(404).body("Student not found");
        }

        // Verify exam exists and is currently active
        Exam exam = examService.getExamBySubcode(Integer.parseInt(subcode));
        if (exam == null) {
            return ResponseEntity.status(404).body("No active exam found for given subject code");
        }

        // Verify student's department and semester match exam requirements
        if (!student.getDepartment().equals(exam.getDepartment()) || 
            student.getSemester() != exam.getSemester()) {
            return ResponseEntity.status(403)
                .body("Student's department/semester does not match exam requirements");
        }

        // Get questions for the exam
        List<Question> questions = questionService.getQuestionsByExamId(exam);
        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.status(404).body("No questions found for this exam");
        }

        // Create response with just question text and options
        List<Map<String, Object>> questionsList = new ArrayList<>();
        for (Question q : questions) {
            Map<String, Object> questionMap = new HashMap<>();
            questionMap.put("questionNo", q.getNumber());
            questionMap.put("questionText", q.getQuestion());
            List<String> options = Arrays.asList(q.getOption_a(), q.getOption_b(), q.getOption_c(), q.getOption_d());
            questionMap.put("options", options);
            questionsList.add(questionMap);
        }

        questionsList.sort(Comparator.comparingInt(q -> (int) q.get("questionNo")));

        Map<String, Object> response = new HashMap<>();
        response.put("student", student);
        response.put("exam", exam);
        response.put("questions", questionsList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit-answers")
    public ResponseEntity<?> submitAnswers(@RequestBody Map<String, Object> request) {
        String regno = (String) request.get("regno");
        int subcode = Integer.parseInt((String) request.get("subcode"));
        List<Map<String, Object>> answers = (List<Map<String, Object>>) request.get("answers");

        Exam exam = examService.getExamBySubcode(subcode);

        // Evaluate answers and calculate marks
        int totalMarks = 0;
        for (Map<String, Object> answer : answers) {
            Integer questionNo =  (Integer) answer.get("questionNo");
            String selectedOption = (String) answer.get("answer");

            // Fetch the corresponding question from the database
            Question question = questionService.getQuestionByExamAndNumber(exam, questionNo);
            if (question != null) {
                // Check if the selected option is correct
                String correctOption = question.getCorrect_option(); // Assuming this method exists
                if (selectedOption.equals(correctOption)) {
                    totalMarks++;
                }
            }
        }

        // Store the result in the results table
        Result result = new Result();
        result.setStudent(studentServide.getStudentById(regno));
        result.setExam(exam);
        result.setSemester(exam.getSemester());
        result.setTotal_marks(exam.getTotalmarks());
        result.setObtained_marks(totalMarks);
        resultService.saveResult(result); // Assuming this method exists

        return ResponseEntity.ok("Answers submitted successfully with marks: " + 5);
    }

    @PostMapping("/results")
    public ResponseEntity<?> getResults(@RequestBody Map<String, String> request) {
        String regno = request.get("regno");
        String name = request.get("name");
        String semester = request.get("semester");
        // Fetch student details
        Student student = studentServide.getStudentById(regno);
        if (student == null) {
            return ResponseEntity.status(404).body("Student not found");
        }

        // Check if the provided name matches the database name
        if (!student.getName().equals(name)) {
            return ResponseEntity.status(403).body("Provided name does not match the registered name");
        }

        // Fetch all results for the student of the specified semester
        List<Result> results = resultService.getResultsByStudentAndSemester(student, Integer.parseInt(semester));
        if (results == null || results.isEmpty()) {
            return ResponseEntity.status(404).body("No results found for this student in the specified semester");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("student", student);
        response.put("results", results);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/results/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return ResponseEntity.ok().build();
    }
}
