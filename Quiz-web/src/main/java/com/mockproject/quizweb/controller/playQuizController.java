package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.domain.QuizHistory;
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
import java.time.format.DateTimeFormatter;
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

    @GetMapping(value = {"/{list_quiz_id}"})
    public @ResponseBody Quiz getQuiz(@PathVariable Integer list_quiz_id,
                                      @RequestParam Integer quiz_number) {
        return listQuizService.getListQuizById(list_quiz_id).getQuizzes().get(quiz_number);
    }

    private String getRemainTime(ListQuiz listQuiz, String username) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        List<QuizHistory> listQuizHistory =
                quizHistoryService.getQuizHistoriesByListQuiz_IdAndAccount_Username(listQuiz.getId(), username);
        String time = listQuiz.getTimeLimit();
        String ret = "This quiz has ended!";
        if (listQuizHistory != null) {
            for (QuizHistory quizHistory: listQuizHistory) {
                if (quizHistory.getTimeAnswered() != null) {
                    continue;
                }
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime start = (LocalDateTime) dtf.parse(quizHistory.getTimeStarted());
                if (now.compareTo(start) > 0) {
                    quizHistory.setTimeAnswered(dtf.format(now));
                    quizHistoryService.update(quizHistory);
                    continue;
                }
                Duration dur = Duration.between(start, now);
                ret = dur.toSeconds() / 60 + ":" +
                        dur.toSeconds() % 60;
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
}
