package com.mockproject.quizweb.repository;

import com.mockproject.quizweb.domain.Answer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerReposity extends JpaRepository<Answer, Integer> {
    
}
