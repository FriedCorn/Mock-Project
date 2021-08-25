package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.AnswerHistory;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.domain.QuizHistory;

public interface AnswerHistoryService {
    void save(AnswerHistory answerHistory);
    void delete(AnswerHistory answerHistory);
    boolean[] getAnswerHistoryByQuiz(QuizHistory quizHistory, Quiz quiz);
}
