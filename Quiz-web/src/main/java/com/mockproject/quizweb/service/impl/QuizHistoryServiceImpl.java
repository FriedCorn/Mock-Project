package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.AnswerHistory;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.QuizHistory;
import com.mockproject.quizweb.repository.ListQuizRepository;
import com.mockproject.quizweb.repository.QuizHistoryRepository;
import com.mockproject.quizweb.service.QuizHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizHistoryServiceImpl implements QuizHistoryService {
    private final QuizHistoryRepository quizHistoryRepository;
    private final ListQuizRepository listQuizRepository;

    @Autowired
    public QuizHistoryServiceImpl(QuizHistoryRepository quizHistoryRepository, ListQuizRepository listQuizRepository) {
        this.quizHistoryRepository = quizHistoryRepository;
        this.listQuizRepository = listQuizRepository;
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

    public QuizHistory getDoingQuiz(int list_quiz_id, String username) {
        return getDoingQuiz(listQuizRepository.getListQuizById(list_quiz_id), username);
    }

    public QuizHistory getDoingQuiz(ListQuiz listQuiz, String username) {
        List<QuizHistory> listQuizHistory =
                getQuizHistoriesByListQuiz_IdAndAccount_Username(listQuiz.getId(), username);
        for (QuizHistory quizHistory: listQuizHistory) {
            if (quizHistory.getTimeAnswered() == null) {
                return quizHistory;
            }
        }
        return null;
    }

    public AnswerHistory getAnswerHistoryByQuizHistoryAndAnswerId(QuizHistory quizHistory, int answerId) {
        for (AnswerHistory answerHistory: quizHistory.getAnswerHistories()) {
            if (answerHistory.getAnswer().getId() == answerId) {
                return answerHistory;
            }
        }
        return null;
    }
}
