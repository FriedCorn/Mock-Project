package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.domain.QuizHistory;
import com.mockproject.quizweb.repository.QuizHistoryRepository;
import com.mockproject.quizweb.service.QuizHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizHistoryServiceImpl implements QuizHistoryService {
    private final QuizHistoryRepository quizHistoryRepository;

    @Autowired
    public QuizHistoryServiceImpl(QuizHistoryRepository quizHistoryRepository) {
        this.quizHistoryRepository = quizHistoryRepository;
    }

    @Override
    public QuizHistory getQuizHistoryByAccount_Id(int id) {
        return quizHistoryRepository.getQuizHistoryByAccount_Id(id);
    }

    @Override
    public List<QuizHistory> getQuizHistoriesByListQuiz_IdAndAccount_Username(int list_id, String username) {
        return quizHistoryRepository.getQuizHistoriesByListQuiz_IdAndAccount_Username(list_id, username);
    }

    @Override
    public void save(QuizHistory quizHistory) {
        quizHistoryRepository.save(quizHistory);
    }

    @Override
    public void update(QuizHistory quizHistory) {
        quizHistoryRepository.save(quizHistory);
    }

}
