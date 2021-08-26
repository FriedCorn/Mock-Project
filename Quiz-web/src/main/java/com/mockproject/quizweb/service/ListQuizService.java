package com.mockproject.quizweb.service;

import java.util.List;

import com.mockproject.quizweb.domain.ListQuiz;

public interface ListQuizService {
    ListQuiz getListQuizById(int id);
    String getRemainTime(ListQuiz listQuiz, String username);
    void create(ListQuiz listQuiz);
    List<ListQuiz> getAllListQuiz();
    List<ListQuiz> getListQuizByAccountUsername(String username);

}
