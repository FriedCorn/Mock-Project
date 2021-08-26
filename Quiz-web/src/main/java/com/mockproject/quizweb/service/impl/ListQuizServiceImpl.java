package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.QuizHistory;
import com.mockproject.quizweb.repository.AccountRepository;
import com.mockproject.quizweb.repository.ListQuizRepository;
import com.mockproject.quizweb.repository.QuizHistoryRepository;
import com.mockproject.quizweb.service.ListQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ListQuizServiceImpl implements ListQuizService {
    private final ListQuizRepository listQuizRepository;
    private final QuizHistoryRepository quizHistoryRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ListQuizServiceImpl(ListQuizRepository listQuizRepository, QuizHistoryRepository quizHistoryRepository, AccountRepository accountRepository) {
        this.listQuizRepository = listQuizRepository;
        this.quizHistoryRepository = quizHistoryRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public ListQuiz getListQuizById(int id) {
        return listQuizRepository.getListQuizById(id);
    }

    @Override
    public String getRemainTime(ListQuiz listQuiz, String username) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        List<QuizHistory> listQuizHistory =
                quizHistoryRepository.getQuizHistoriesByListQuiz_IdAndAccount_Username(listQuiz.getId(), username);
        String time = listQuiz.getTimeLimit();
        Duration durTime = Duration.between(LocalTime.parse("00:00:00", dtf2), LocalTime.parse(time, dtf2));
        long remTime = durTime.getSeconds();
        String ret = "This quiz has ended!";
        if (listQuizHistory.size() != 0) {
            for (QuizHistory quizHistory: listQuizHistory) {
                if (quizHistory.getTimeAnswered() != null) {
                    continue;
                }
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime end = LocalDateTime.parse(quizHistory.getTimeStarted(), dtf).plusSeconds(remTime);
                if (now.compareTo(end) >= 0) {
                    quizHistory.setTimeAnswered(dtf.format(now));
                    quizHistoryRepository.save(quizHistory);
                    continue;
                }
                Duration dur = Duration.between(now, end);
                ret = dur.toHours() + ":" + dur.toMinutes() % 60
                        + ":" + dur.getSeconds() % 60;
            }
        }
        else {
            QuizHistory quizHistory = new QuizHistory();
            quizHistory.setTimeStarted(dtf.format(LocalDateTime.now()));
            quizHistory.setAccount(accountRepository.findAccountByUsername(username));
            quizHistory.setListQuiz(listQuiz);
            quizHistoryRepository.save(quizHistory);
            ret = listQuiz.getTimeLimit();
        }
        return ret;
    }

    @Override
    public void create(ListQuiz listQuiz) {
        listQuizRepository.save(listQuiz);
    }

    @Override
    public List<ListQuiz> getAllListQuiz() {
        return listQuizRepository.findAll();
    }
}
