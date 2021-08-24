package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.repository.QuizRepository;
import com.mockproject.quizweb.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    @Autowired
    QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public void delete(Quiz quiz) {
        quizRepository.delete(quiz);
    }

    @Override
    public void create(Quiz quiz) {
      quizRepository.save(quiz);
    }
}
