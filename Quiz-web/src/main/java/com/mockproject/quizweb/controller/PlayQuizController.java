package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.*;
import com.mockproject.quizweb.service.AccountService;
import com.mockproject.quizweb.service.AnswerHistoryService;
import com.mockproject.quizweb.service.ListQuizService;
import com.mockproject.quizweb.service.QuizHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;

@Controller
@RequestMapping("/play-quiz")
public class PlayQuizController {
    private final ListQuizService listQuizService;
    private final QuizHistoryService quizHistoryService;
    private final AnswerHistoryService answerHistoryService;

    @Autowired
    public PlayQuizController(ListQuizService listQuizService, QuizHistoryService quizHistoryService, AnswerHistoryService answerHistoryService) {
        this.listQuizService = listQuizService;
        this.quizHistoryService = quizHistoryService;
        this.answerHistoryService = answerHistoryService;
    }

    @GetMapping(value = {"/{list_quiz_id}"})
    public String getListQuiz(Model model,
                              @PathVariable Integer list_quiz_id,
                              Principal principal) {
        System.out.println(principal.getName());

        ListQuiz listQuiz = listQuizService.getListQuizById(list_quiz_id);
        String remainTime = listQuizService.getRemainTime(listQuiz, principal.getName());
        QuizHistory quizHistory = quizHistoryService.getDoingQuiz(listQuiz, principal.getName());
        if (remainTime.compareTo("This quiz has ended!") == 0) {
            quizHistory = quizHistoryService.newQuizHistory(listQuiz, principal.getName());
            remainTime = listQuizService.getRemainTime(listQuiz, principal.getName());
        }
        Quiz quiz = listQuiz.getQuizzes().get(0);
        model.addAttribute("time", remainTime);
        model.addAttribute("list_quiz_id", list_quiz_id);
        model.addAttribute("current", 1);
        model.addAttribute("total", listQuiz.getNumberOfQuiz());
        model.addAttribute("quiz", quiz);
        if (quizHistory != null) {
            if (quizHistory.getAnswerHistories() != null) {
                boolean[] oldAns = quizHistoryService.getAnswerHistoryByQuiz(quizHistory, quiz);
                model.addAttribute("oldAns", oldAns);
            }
        }
        return "playQuiz";
    }

    @GetMapping(value = {"/{list_quiz_id}/quiz"}, produces = "application/json")
    public @ResponseBody Hashtable<String, Object>
    getQuiz(@PathVariable Integer list_quiz_id,
            @RequestParam Integer quiz_number,
            Principal principal) {
        Hashtable<String, Object> ret = new Hashtable<>();
        ListQuiz listQuiz = listQuizService.getListQuizById(list_quiz_id);
        Quiz quiz = listQuiz.getQuizzes().get(quiz_number);
        ret.put("quiz", quiz);
        QuizHistory quizHistory = quizHistoryService.getDoingQuiz(listQuiz, principal.getName());
        boolean[] ansHistoryOfQuiz = quizHistoryService.getAnswerHistoryByQuiz(quizHistory, quiz);
        ret.put("answer", ansHistoryOfQuiz);
        return ret;
    }

    @PostMapping(value = "/{list_quiz_id}", produces = "application/json")
    public @ResponseBody String updateAnswer(@PathVariable Integer list_quiz_id,
                                             @RequestParam Integer quiz_number,
                                             @RequestParam Boolean answer_A,
                                             @RequestParam Boolean answer_B,
                                             @RequestParam Boolean answer_C,
                                             @RequestParam Boolean answer_D,
                                             Principal principal) {
        boolean[] answer = {answer_A, answer_B, answer_C, answer_D};
        ListQuiz listQuiz = listQuizService.getListQuizById(list_quiz_id);
        QuizHistory quizHistory = quizHistoryService.getDoingQuiz(listQuiz, principal.getName());
        Quiz quiz = listQuiz.getQuizzes().get(quiz_number);
        answerHistoryService.updateAnswerHistory(answer, quiz, quizHistory);
        return "success";
    }

    @GetMapping(value = "/{list_quiz_id}/summit")
    public String finishQuiz(Model model, @PathVariable Integer list_quiz_id,
                                           Principal principal) {
        QuizHistory quizHistory = quizHistoryService.getDoingQuiz(list_quiz_id, principal.getName());
        if (quizHistory != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            quizHistory.setTimeAnswered(dtf.format(now));
            quizHistoryService.save(quizHistory);
        }
        else {
            quizHistory = quizHistoryService.getQuizHistoriesByListQuiz_IdAndAccount_Username(list_quiz_id, principal.getName()).get(0);
        }
        model.addAttribute("listQuiz", listQuizService.getListQuizById(list_quiz_id));
        model.addAttribute("count", quizHistoryService.countTrueQuiz(quizHistory));
        model.addAttribute("total", quizHistory.getListQuiz().getNumberOfQuiz());
        return "quizResult";
    }




}
