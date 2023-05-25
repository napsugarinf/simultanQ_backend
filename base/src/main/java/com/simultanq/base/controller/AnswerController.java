package com.simultanq.base.controller;


import com.simultanq.base.entity.Answer;
import com.simultanq.base.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping
    public List<Answer> getAllAnswers() {
        return answerService.getAllAnswers();
    }

    @GetMapping("/{id}")
    public Answer getAnswerById(@PathVariable Long id) {
        return answerService.getAnswerById(id);
    }

    @PostMapping
    public Answer createAnswer(@RequestBody Answer answer) {
        return answerService.createAnswer(answer);
    }

    @PutMapping("/{id}")
    public Answer updateAnswer(@PathVariable Long id, @RequestBody Answer updatedAnswer) {
        return answerService.updateAnswer(id, updatedAnswer);
    }

    @DeleteMapping("/{id}")
    public void deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
    }
}
