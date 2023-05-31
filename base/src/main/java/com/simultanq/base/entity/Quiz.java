package com.simultanq.base.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String PIN=generateRandomPIN();

    //@NotBlank
    private String title;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private List<Question> questions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private List<Result> results;

    public Quiz() {
    }

    public Quiz(String title, List<Question> questions) {
        this.title = title;
        this.questions = questions;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public void setResults(List<Result> results) {
        this.results = results;
    }


    public String generateRandomPIN() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000; // Generate a 6-digit number
        return String.valueOf(randomNumber);
    }

//    public boolean isAvailable() {
//        return isAvailable;
//    }
//
//    public void setAvailable(boolean available) {
//        isAvailable = available;
//    }
}



