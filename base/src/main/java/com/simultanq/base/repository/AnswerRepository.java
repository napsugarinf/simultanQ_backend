package com.simultanq.base.repository;

import com.simultanq.base.entity.Answer;
import com.simultanq.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findById(Long Id);
}