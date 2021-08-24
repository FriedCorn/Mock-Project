package com.mockproject.quizweb.repository;

import com.mockproject.quizweb.domain.AnswerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerHistoryRepository extends JpaRepository<AnswerHistory, Integer> {

}
