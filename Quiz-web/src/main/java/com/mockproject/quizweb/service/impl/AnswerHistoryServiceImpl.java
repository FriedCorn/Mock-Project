package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.AnswerHistory;
import com.mockproject.quizweb.repository.AnswerHistoryRepository;
import com.mockproject.quizweb.service.AnswerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerHistoryServiceImpl implements AnswerHistoryService {
    private final AnswerHistoryRepository answerHistoryRepository;

    @Autowired
    public AnswerHistoryServiceImpl(AnswerHistoryRepository answerHistoryRepository) {
        this.answerHistoryRepository = answerHistoryRepository;
    }

    @Override
    public void save(AnswerHistory answerHistory) {
        answerHistoryRepository.save(answerHistory);
    }
}
