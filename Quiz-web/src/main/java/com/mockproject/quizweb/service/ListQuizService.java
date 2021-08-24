package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.ListQuiz;

public interface ListQuizService {
    ListQuiz getListQuizById(int id);
     void create(ListQuiz listQuiz);

}
