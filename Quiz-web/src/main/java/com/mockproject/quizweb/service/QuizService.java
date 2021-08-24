package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.Quiz;

public interface QuizService {
    void delete(Quiz quiz);

    void update(Quiz quiz);
}
