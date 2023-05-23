package com.simultanq.base.repository;

import com.simultanq.base.entity.Quiz;
import com.simultanq.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findByPIN(String PIN);
}
