package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.repository.ListQuizRepository;
import com.mockproject.quizweb.service.ListQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListQuizServiceImpl implements ListQuizService {
    private final ListQuizRepository listQuizRepository;

    @Autowired
    public ListQuizServiceImpl(ListQuizRepository listQuizRepository) {
        this.listQuizRepository = listQuizRepository;
    }

    @Override
    public ListQuiz getListQuizById(int id) {
        return listQuizRepository.getListQuizById(id);
    }
}
