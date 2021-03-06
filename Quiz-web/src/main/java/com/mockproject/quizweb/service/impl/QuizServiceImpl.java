package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.Answer;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.repository.QuizRepository;
import com.mockproject.quizweb.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public void create(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public void delete(Quiz quiz) {
        quizRepository.delete(quiz);
    }

    @Override
    public void update(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public Quiz getQuizById(int quiz_id) {
        return quizRepository.getQuizById(quiz_id);
    }
}
