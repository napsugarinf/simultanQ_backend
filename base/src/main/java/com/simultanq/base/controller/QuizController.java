package com.simultanq.base.controller;


import com.simultanq.base.entity.Answer;
import com.simultanq.base.entity.Question;
import com.simultanq.base.entity.Quiz;
import com.simultanq.base.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

//    @GetMapping("/pin/{PIN}")
//    public ResponseEntity<Quiz> getQuizByPIN(@PathVariable String PIN) {
//        Quiz quiz = quizService.getQuizByPIN(PIN);
//
//        if (quiz.isAvailable()) {
//            return ResponseEntity.ok(quiz);
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }

    @PostMapping("/addQuiz")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @PutMapping("/{id}")
    public Quiz updateQuiz(@PathVariable Long id, @RequestBody Quiz updatedQuiz) {
        return quizService.updateQuiz(id, updatedQuiz);
    }
//    @PutMapping("/makeQuizVisible/{pin}")
//    public Quiz makeQuizVisible(@PathVariable String pin) {
//        return quizService.makeQuizVisible(pin);
//    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }

    @PostMapping("/{PIN}/submitAnswers")
    public ResponseEntity<String> submitAnswers(
            @PathVariable String pin,
            @RequestBody List<Answer> userAnswers
    ) {
        Quiz quiz = quizService.getQuizByPIN(pin);

        // Get the questions from the quiz
        List<Question> questions = quiz.getQuestions();

        // Validate the submitted answers
        if (userAnswers.size() != questions.size()) {
            return ResponseEntity.badRequest().body("Invalid number of answers submitted.");
        }

        // Calculate the score
        int score = calculateScore(userAnswers, questions);

        // Perform any other required processing based on your scoring logic

        // Return the score
        return ResponseEntity.ok("Your score: " + score);
    }

    private int calculateScore(List<Answer> userAnswers, List<Question> questions) {
        int score = 0;

        for (int i = 0; i < userAnswers.size(); i++) {
            Answer userAnswer = userAnswers.get(i);
            Question question = questions.get(i);

            // Compare the user's answer with the correct answer for each question
            if (userAnswer.isCorrect() && userAnswer.getId().equals(question.getCorrectAnswerId())) {
                score++;
            }
        }

        return score;


    }
}


