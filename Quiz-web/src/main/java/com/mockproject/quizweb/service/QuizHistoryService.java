package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.AnswerHistory;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.QuizHistory;

import java.util.List;

public interface QuizHistoryService {
    QuizHistory getQuizHistoryByAccount_Id(int id);
    List<QuizHistory> getQuizHistoriesByListQuiz_IdAndAccount_Username(int list_id, String username);
    void save(QuizHistory quizHistory);
    void update(QuizHistory quizHistory);
    QuizHistory getDoingQuiz(int list_quiz_id, String username);
    QuizHistory getDoingQuiz(ListQuiz listQuiz, String username);
    AnswerHistory getAnswerHistoryByQuizHistoryAndAnswerId(QuizHistory quizHistory, int answerId);
    QuizHistory newQuizHistory(ListQuiz listQuiz, String username);
}
