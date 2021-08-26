package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.Answer;
import com.mockproject.quizweb.domain.AnswerHistory;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.domain.QuizHistory;
import com.mockproject.quizweb.repository.AnswerHistoryRepository;
import com.mockproject.quizweb.service.AnswerHistoryService;
import com.mockproject.quizweb.service.QuizHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerHistoryServiceImpl implements AnswerHistoryService {
    private final AnswerHistoryRepository answerHistoryRepository;
    private final QuizHistoryService quizHistoryService;

    @Autowired
    public AnswerHistoryServiceImpl(AnswerHistoryRepository answerHistoryRepository, QuizHistoryService quizHistoryService) {
        this.answerHistoryRepository = answerHistoryRepository;
        this.quizHistoryService = quizHistoryService;
    }

    @Override
    public void save(AnswerHistory answerHistory) {
        answerHistoryRepository.save(answerHistory);
    }

    @Override
    public void delete(AnswerHistory answerHistory) {
        answerHistoryRepository.delete(answerHistory);
    }

    @Override
    public void delete(int ansHistory) {
        answerHistoryRepository.deleteById(ansHistory);
    }

    @Override
    public void updateAnswerHistory(boolean[] newAns, Quiz quiz, QuizHistory quizHistory) {
        boolean[] oldAns = quizHistoryService.getAnswerHistoryByQuiz(quizHistory, quiz);
        if (quizHistory.getAnswerHistories() == null) {
            quizHistory.setAnswerHistories(new ArrayList<>());
        }
        for (int i = 0; i < 4; i++) {
            if (newAns[i] ^ oldAns[i]) { // Xor
                if (newAns[i]) {
                    AnswerHistory answerHistory = new AnswerHistory();
                    answerHistory.setAnswer(quiz.getAnswers().get(i));
                    answerHistory.setQuizHistoryByQuizHistoryId(quizHistory);
                    quizHistory.getAnswerHistories().add(answerHistory);
                }
                else {
                    AnswerHistory answerHistory = quizHistoryService.getAnswerHistoryByQuizHistoryAndAnswerId(quizHistory,
                            quiz.getAnswers().get(i).getId());
                    quizHistory.getAnswerHistories().remove(answerHistory);
                    answerHistory.setQuizHistoryByQuizHistoryId(null);
                    delete(answerHistory);
                }
            }
        }
        quizHistoryService.save(quizHistory);
    }
}
