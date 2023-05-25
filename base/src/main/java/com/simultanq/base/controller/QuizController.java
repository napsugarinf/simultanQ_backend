package com.simultanq.base.controller;


import com.simultanq.base.entity.Quiz;
import com.simultanq.base.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private
    QuizService quizService;

    @GetMapping
    public List<Quiz> getAllQuiz() {
        return quizService.getAllQuiz();
    }

    @GetMapping("/id/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/pin/{PIN}")
    public Quiz getQuizByPIN(@PathVariable String PIN) {
        return quizService.getQuizByPIN(PIN);
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @PutMapping("/{id}")
    public Quiz updateQuiz(@PathVariable Long id, @RequestBody Quiz updatedQuiz) {
        return quizService.updateQuiz(id, updatedQuiz);
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }
}


