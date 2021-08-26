package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.*;

import java.util.List;

public interface QuizHistoryService {
    QuizHistory getQuizHistoryByAccount_Id(int id);
    List<QuizHistory> getQuizHistoriesByListQuiz_IdAndAccount_Username(int list_id, String username);
    void save(QuizHistory quizHistory);
    void update(QuizHistory quizHistory);
    QuizHistory getDoingQuiz(int list_quiz_id, String username);
    QuizHistory getDoingQuiz(ListQuiz listQuiz, String username);
    List<ListQuiz> getDoingQuizzes(String username);
    List<ListQuiz> getFinishedQuizzes(String username);
    AnswerHistory getAnswerHistoryByQuizHistoryAndAnswerId(QuizHistory quizHistory, int answerId);
    QuizHistory newQuizHistory(ListQuiz listQuiz, String username);
    int countTrueQuiz(QuizHistory quizHistory);
    boolean[] getAnswerHistoryByQuiz(QuizHistory quizHistory, Quiz quiz);
    List<QuizHistory> getQuizHistoriesByListQuiz_Id(int list_id);
    List<Account> getAccountsOfQuizHistoriesByListQuiz_Id(int list_id);
    List<QuizHistory> getQuizHistoriesByListQuiz_IdAndAccount_Id(int list_id, int acc_id);
    Float calculateMean(List<QuizHistory> quizHistoryList);
}
