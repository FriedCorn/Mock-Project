package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.*;
import com.mockproject.quizweb.repository.AccountRepository;
import com.mockproject.quizweb.repository.ListQuizRepository;
import com.mockproject.quizweb.repository.QuizHistoryRepository;
import com.mockproject.quizweb.service.QuizHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuizHistoryServiceImpl implements QuizHistoryService {
    private final QuizHistoryRepository quizHistoryRepository;
    private final ListQuizRepository listQuizRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public QuizHistoryServiceImpl(QuizHistoryRepository quizHistoryRepository, ListQuizRepository listQuizRepository, AccountRepository accountRepository) {
        this.quizHistoryRepository = quizHistoryRepository;
        this.listQuizRepository = listQuizRepository;
        this.accountRepository = accountRepository;
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

    @Override
    public List<ListQuiz> getDoingQuizzes(String username) {
        int account_id = accountRepository.findAccountByUsername(username).getId();
        List<QuizHistory> quizHistories = quizHistoryRepository.getQuizHistoriesByAccount_Id(account_id);
        List<ListQuiz> listQuizzes = new ArrayList<>();
        for (QuizHistory quizHistory: quizHistories) {
            if (quizHistory.getTimeAnswered() == null) {
                listQuizzes.add(quizHistory.getListQuiz());
            }
        }
        return (listQuizzes.size() == 0)?null:listQuizzes;
    }

    @Override
    public List<ListQuiz> getFinishedQuizzes(String username) {
        int account_id = accountRepository.findAccountByUsername(username).getId();
        List<QuizHistory> quizHistories = quizHistoryRepository.getQuizHistoriesByAccount_Id(account_id);
        List<ListQuiz> listQuizzes = new ArrayList<>();
        for (QuizHistory quizHistory: quizHistories) {
            if (quizHistory.getTimeAnswered() != null) {
                listQuizzes.add(quizHistory.getListQuiz());
            }
        }
        return (listQuizzes.size() == 0)?null:listQuizzes;
    }

    public AnswerHistory getAnswerHistoryByQuizHistoryAndAnswerId(QuizHistory quizHistory, int answerId) {
        for (AnswerHistory answerHistory: quizHistory.getAnswerHistories()) {
            if (answerHistory.getAnswer().getId() == answerId) {
                return answerHistory;
            }
        }
        return null;
    }

    @Override
    public QuizHistory newQuizHistory(ListQuiz listQuiz, String username) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        QuizHistory quizHistory = new QuizHistory();
        quizHistory.setTimeStarted(dtf.format(LocalDateTime.now()));
        quizHistory.setAccount(accountRepository.findAccountByUsername(username));
        quizHistory.setListQuiz(listQuiz);
        quizHistoryRepository.save(quizHistory);
        return quizHistory;
    }

    @Override
    public int countTrueQuiz(QuizHistory quizHistory) {
        int count = 0;
        ListQuiz listQuiz = quizHistory.getListQuiz();
        for (Quiz quiz: listQuiz.getQuizzes()) {
            boolean[] ansHistory = getAnswerHistoryByQuiz(quizHistory, quiz);
            boolean isFalse = false;
            for (int i = 0; i < 4; i++) {
                if (ansHistory[i] ^ quiz.getAnswers().get(i).isTrueAnswer()) {
                    isFalse = true;
                    break;
                }
            }
            if (!isFalse) {
                count++;
            }
        }
        return count;
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

    @Override
    public List<QuizHistory> getQuizHistoriesByListQuiz_Id(int list_id) {
        return quizHistoryRepository.getQuizHistoriesByListQuiz_Id(list_id);
    }

    @Override
    public List<Account> getAccountsOfQuizHistoriesByListQuiz_Id(int list_id) {
        List<QuizHistory> quizHistoryList = getQuizHistoriesByListQuiz_Id(list_id);
        List<Account> accountList = new ArrayList<>();
        for (QuizHistory quizHistory: quizHistoryList) {
            accountList.add(quizHistory.getAccount());
        }
        Set<Account> accountSet = new HashSet<>(accountList);
        accountList = new ArrayList<>(accountSet);
        return accountList;
    }

    @Override
    public List<QuizHistory> getQuizHistoriesByListQuiz_IdAndAccount_Id(int list_id, int acc_id) {
        return quizHistoryRepository.getQuizHistoriesByListQuiz_IdAndAccount_Id(list_id,acc_id);
    }

    @Override
    public Float calculateMean(List<QuizHistory> quizHistoryList) {
        int totalAnswer = 0;
        int totalTrueAnswer = 0;
        for (QuizHistory quizHistory: quizHistoryList) {
            totalAnswer += quizHistory.getListQuiz().getNumberOfQuiz();
            totalTrueAnswer += countTrueQuiz(quizHistory);
        }
        if (totalAnswer == 0) {
            return 1F;
        }
        else {
            return (float) totalTrueAnswer / (float) totalAnswer;
        }
    }
}
