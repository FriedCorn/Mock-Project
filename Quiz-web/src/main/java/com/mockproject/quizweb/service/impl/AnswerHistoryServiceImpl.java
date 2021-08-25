package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.Answer;
import com.mockproject.quizweb.domain.AnswerHistory;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.domain.QuizHistory;
import com.mockproject.quizweb.repository.AnswerHistoryRepository;
import com.mockproject.quizweb.service.AnswerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void delete(AnswerHistory answerHistory) {
        answerHistoryRepository.delete(answerHistory);
    }

    public boolean[] getAnswerHistoryByQuiz(QuizHistory quizHistory, Quiz quiz) {
        List<Answer> answers = new ArrayList<>();
        for (AnswerHistory answerHistory: quizHistory.getAnswerHistories()) {
            if (answerHistory.getAnswer().getQuizByQuizId() == quiz) {
                answers.add(answerHistory.getAnswer());
            }
        }
        boolean[] ret = {false, false, false, false};
        int index = 0;
        for (Answer answer: quiz.getAnswers()) {
            if (answers.contains(answer)) {
                ret[index++] = true;
            }
            else {
                index++;
            }
        }
        return ret;
    }
}
