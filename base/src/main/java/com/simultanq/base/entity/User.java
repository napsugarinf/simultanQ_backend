package com.simultanq.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "users")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
//public class User {
//    @Id
//    @JsonIgnore
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_gen")
//    @SequenceGenerator(name = "users_gen", sequenceName = "users_seq", allocationSize = 1)
//    @ToString.Exclude
//    @EqualsAndHashCode.Include
//    @Column(name = "id")
//    private Long id;
//
//    @NotBlank(message = "Username is mandatory")
//    private String username;
//
//    @ToString.Exclude
//    private String password;
//
//    @ToString.Exclude
//    private String token;
//}


    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        //@NotBlank
        @Column(unique = true)
        private String username;

        //@NotBlank
        private String password;

        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "quiz_id")
        private List<Quiz> quizzes;

        // constructors, getters, and setters
    }

