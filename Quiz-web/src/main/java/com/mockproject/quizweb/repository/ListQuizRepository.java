package com.mockproject.quizweb.repository;

import com.mockproject.quizweb.domain.ListQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListQuizRepository extends JpaRepository<ListQuiz, Integer> {
    ListQuiz getListQuizById(int id);
}
