package com.mockproject.quizweb.repository;

import com.mockproject.quizweb.domain.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
     Quiz getQuizById(int id);
}
