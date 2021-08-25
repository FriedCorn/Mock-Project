package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.ListQuiz;

public interface ListQuizService {
    ListQuiz getListQuizById(int id);
    String getRemainTime(ListQuiz listQuiz, String username);
    void create(ListQuiz listQuiz);

}
