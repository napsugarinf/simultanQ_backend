package com.simultanq.base.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank
    private String text;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<Answer> answers;


    @Transient
    private Long correctAnswerId;
    public Question() {

    }

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }


    public Long getCorrectAnswerId() {
        if (correctAnswerId != null) {
            return correctAnswerId;
        }

        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                correctAnswerId = answer.getId();
                break;
            }
        }

        return correctAnswerId;
    }
}

