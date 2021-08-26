package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.Quiz;

public interface QuizService {
    void create(Quiz quiz);
    void delete(Quiz quiz);
    void update(Quiz quiz);
    public Quiz getQuizById(int quiz_id);
}
