package com.simultanq.base.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "quizes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long PIN;

    //@NotBlank
    private String title;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private List<Question> questions;


}



