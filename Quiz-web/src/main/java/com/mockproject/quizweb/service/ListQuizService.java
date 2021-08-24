package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.ListQuiz;

public interface ListQuizService {
    void create(ListQuiz listQuiz);

    ListQuiz getListQuizById(int id);
}
