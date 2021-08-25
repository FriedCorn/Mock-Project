package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.Quiz;

public interface QuizService {
    void create(Quiz quiz);
    void delete(Quiz quiz);
    void update(Quiz quiz);
    Quiz getQuizById(int quiz_id);
}
