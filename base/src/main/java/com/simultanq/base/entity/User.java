package com.simultanq.base.entity;

import jakarta.persistence.*;

@Entity
    @Table(name = "users")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        //@NotBlank
        @Column(unique = true)
        private String username;

        //@NotBlank
        private String password;

        // constructors, getters, and setters
    }

