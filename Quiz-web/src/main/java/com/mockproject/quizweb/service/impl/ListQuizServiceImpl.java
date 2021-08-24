package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.repository.ListQuizRepository;
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
     public void create(ListQuiz listQuiz) {
          listQuizRepository.save(listQuiz);
     }

    @Override
    public ListQuiz getListQuizById(int id) {
        return listQuizRepository.getListQuizById(id);
    }
}
