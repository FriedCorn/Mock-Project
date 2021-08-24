package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.service.ListQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListQuizServiceImpl implements ListQuizService {
    private final ListQuizService listQuizService;

    @Autowired
    public ListQuizServiceImpl(ListQuizService listQuizService) {
        this.listQuizService = listQuizService;
    }

    @Override
    public ListQuiz getListQuizById(int id) {
        return listQuizService.getListQuizById(id);
    }
}
