package com.mockproject.quizweb.repository;

import com.mockproject.quizweb.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
     Quiz getQuizById(int quiz_id);
}
