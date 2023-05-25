package com.simultanq.base.service;

import com.simultanq.base.entity.Quiz;
import com.simultanq.base.repository.QuestionRepository;
import com.simultanq.base.repository.QuizRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Long id) {
        try {
            return quizRepository.findById(id)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Quiz getQuizByPIN(String PIN) {
        try {
            return quizRepository.findByPIN(PIN)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    public Optional<Quiz> getQuizByPIN(String PIN){
//        Optional <Quiz> quiz = quizRepository.findByPIN(PIN);
//        if (quiz.isEmpty()){
//            throw new ServiceException("Sorry, no quiz by this PIN!");
//
//        }
//        return quiz;
//    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Long id, Quiz updatedQuiz) {
        Quiz quiz = getQuizById(id);
        quiz.setPIN(updatedQuiz.getPIN());
        quiz.setQuestions(updatedQuiz.getQuestions());
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}
