package com.examinationsystem.examinationsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examinationsystem.examinationsystem.model.Exam;
import com.examinationsystem.examinationsystem.model.Question;
import com.examinationsystem.examinationsystem.repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public List<Question> getQuestionsByExamId(Exam subcode) {
        return questionRepository.findByExam(subcode);
    }

    public Question getQuestionByExamAndNumber(Exam subcode, int questionNo) {
        return questionRepository.findByExamAndNumber(subcode, questionNo);
    }

    public Iterable<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Question updateQuestion(Question question) {
        if (questionRepository.existsById(question.getId())) {
            return questionRepository.save(question);
        }
        return null;
    }
}
