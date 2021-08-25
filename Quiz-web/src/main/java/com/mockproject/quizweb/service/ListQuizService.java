package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.ListQuiz;

public interface ListQuizService {
<<<<<<< HEAD
    void create(ListQuiz listQuiz);

    ListQuiz getListQuizById(int id);
=======
    ListQuiz getListQuizById(int id);
    String getRemainTime(ListQuiz listQuiz, String username);
    void create(ListQuiz listQuiz);

>>>>>>> e2eaccbe1bbc8ee73b9fa09ea93a747f871d8d69
}
