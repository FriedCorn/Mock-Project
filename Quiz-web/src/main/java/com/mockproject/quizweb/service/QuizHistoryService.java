package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.QuizHistory;

import java.util.List;

public interface QuizHistoryService {
    QuizHistory getQuizHistoryByAccount_Id(int id);
    List<QuizHistory> getQuizHistoriesByListQuiz_IdAndAccount_Username(int list_id, String username);
    void save(QuizHistory quizHistory);
    void update(QuizHistory quizHistory);
}
