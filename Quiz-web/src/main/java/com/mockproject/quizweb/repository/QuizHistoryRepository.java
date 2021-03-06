package com.mockproject.quizweb.repository;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.domain.QuizHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizHistoryRepository extends JpaRepository<QuizHistory, Integer> {
    QuizHistory getQuizHistoryByAccount_Id(int account_id);
    List<QuizHistory> getQuizHistoriesByAccount_Id(int account_id);
    List<QuizHistory> getQuizHistoriesByListQuiz_IdAndAccount_Username(int list_id, String username);
    List<QuizHistory> getQuizHistoriesByListQuiz_Id(int list_id);
    List<QuizHistory> getQuizHistoriesByListQuiz_IdAndAccount_Id(int list_id, int acc_id);
}
