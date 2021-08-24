package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.*;
import com.mockproject.quizweb.service.AccountService;
import com.mockproject.quizweb.service.ListQuizService;
import com.mockproject.quizweb.service.QuizHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/play-quiz")
public class PlayQuizController {
    private final ListQuizService listQuizService;
    private final QuizHistoryService quizHistoryService;
    private final AccountService accountService;

    @Autowired
    public PlayQuizController(ListQuizService listQuizService, QuizHistoryService quizHistoryService, AccountService accountService) {
        this.listQuizService = listQuizService;
        this.quizHistoryService = quizHistoryService;
        this.accountService = accountService;
    }

    @GetMapping(value = {"/{list_quiz_id}"})
    public String getListQuiz(Model model,
                              @PathVariable Integer list_quiz_id,
                              Principal principal) {
        ListQuiz listQuiz = listQuizService.getListQuizById(list_quiz_id);
        String remainTime = getRemainTime(listQuiz, principal.getName());
        if (remainTime.compareTo("This quiz has ended!") == 0) {
            model.addAttribute("error", remainTime);
        }
        model.addAttribute("time", remainTime);
        model.addAttribute("current", 1);
        model.addAttribute("total", listQuiz.getNumberOfQuiz());
        model.addAttribute("quiz", listQuiz.getQuizzes().get(0));
        return "playQuiz";
    }

    @GetMapping(value = {"/{list_quiz_id}/quiz"}, produces = "application/json")
    public @ResponseBody Quiz getQuiz(@PathVariable Integer list_quiz_id,
                                      @RequestParam Integer quiz_number) {
        return listQuizService.getListQuizById(list_quiz_id).getQuizzes().get(quiz_number);
    }

    @PostMapping(value = "/{list_quiz_id}", produces = "application/json")
    public @ResponseBody String updateAnswer(@PathVariable Integer list_quiz_id,
                                             @RequestParam Integer quiz_number,
                                             @RequestParam String answer_string,
                                             Principal principal) {
        ListQuiz listQuiz = listQuizService.getListQuizById(list_quiz_id);
        QuizHistory quizHistory = getDoingQuiz(listQuiz, principal.getName());
        Quiz quiz = listQuiz.getQuizzes().get(quiz_number);
        List<AnswerHistory> answerHistories = new ArrayList<>();
        if (answer_string.contains("A")) {
            AnswerHistory answerHistory = new AnswerHistory();
            answerHistory.setAnswer(quiz.getAnswers().get(0));
            answerHistory.setQuizHistoryByQuizHistoryId(quizHistory);
            answerHistories.add(answerHistory);
        }
        if (answer_string.contains("B")) {
            AnswerHistory answerHistory = new AnswerHistory();
            answerHistory.setAnswer(quiz.getAnswers().get(1));
            answerHistory.setQuizHistoryByQuizHistoryId(quizHistory);
            answerHistories.add(answerHistory);
        }
        if (answer_string.contains("C")) {
            AnswerHistory answerHistory = new AnswerHistory();
            answerHistory.setAnswer(quiz.getAnswers().get(2));
            answerHistory.setQuizHistoryByQuizHistoryId(quizHistory);
            answerHistories.add(answerHistory);
        }
        if (answer_string.contains("D")) {
            AnswerHistory answerHistory = new AnswerHistory();
            answerHistory.setAnswer(quiz.getAnswers().get(3));
            answerHistory.setQuizHistoryByQuizHistoryId(quizHistory);
            answerHistories.add(answerHistory);
        }
        assert quizHistory != null;
        quizHistory.setAnswerHistories(answerHistories);
        quizHistoryService.save(quizHistory);
        return "success";
    }

    private String getRemainTime(ListQuiz listQuiz, String username) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        List<QuizHistory> listQuizHistory =
                quizHistoryService.getQuizHistoriesByListQuiz_IdAndAccount_Username(listQuiz.getId(), username);
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
                    quizHistoryService.update(quizHistory);
                    continue;
                }
                Duration dur = Duration.between(now, end);
                ret = dur.toHours() + ":" + dur.toMinutes() % 60
                        + ":" + dur.toSeconds() % 60;
            }
        }
        else {
            QuizHistory quizHistory = new QuizHistory();
            quizHistory.setTimeStarted(dtf.format(LocalDateTime.now()));
            quizHistory.setAccount(accountService.findAccountByUsername(username));
            quizHistory.setListQuiz(listQuiz);
            quizHistoryService.save(quizHistory);
            ret = listQuiz.getTimeLimit();
        }
        return ret;
    }

    private QuizHistory getDoingQuiz(ListQuiz listQuiz, String username) {
        List<QuizHistory> listQuizHistory =
                quizHistoryService.getQuizHistoriesByListQuiz_IdAndAccount_Username(listQuiz.getId(), username);
        for (QuizHistory quizHistory: listQuizHistory) {
            if (quizHistory.getTimeAnswered() == null) {
                return quizHistory;
            }
        }
        return null;
    }
}
