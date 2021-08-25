package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.*;
import com.mockproject.quizweb.service.AnswerHistoryService;
import com.mockproject.quizweb.service.ListQuizService;
import com.mockproject.quizweb.service.QuizHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
        ListQuiz listQuiz = listQuizService.getListQuizById(list_quiz_id);
        String remainTime = listQuizService.getRemainTime(listQuiz, principal.getName());
        if (remainTime.compareTo("This quiz has ended!") == 0) {
            model.addAttribute("error", remainTime);
        }
        model.addAttribute("time", remainTime);
        model.addAttribute("list_quiz_id", list_quiz_id);
        model.addAttribute("current", 1);
        model.addAttribute("total", listQuiz.getNumberOfQuiz());
        model.addAttribute("quiz", listQuiz.getQuizzes().get(0));
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
        boolean[] ansHistoryOfQuiz = answerHistoryService.getAnswerHistoryByQuiz(quizHistory, quiz);
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
        assert quizHistory != null;
        boolean[] oldAns = answerHistoryService.getAnswerHistoryByQuiz(quizHistory, quiz);
        if (quizHistory.getAnswerHistories() == null) {
            quizHistory.setAnswerHistories(new ArrayList<>());
        }
        for (int i = 0; i < 4; i++) {
            if (answer[i] ^ oldAns[i]) {
                if (answer[i]) {
                    AnswerHistory answerHistory = new AnswerHistory();
                    answerHistory.setAnswer(quiz.getAnswers().get(i));
                    answerHistory.setQuizHistoryByQuizHistoryId(quizHistory);
                    quizHistory.getAnswerHistories().add(answerHistory);
                }
                else {
                    AnswerHistory answerHistory = quizHistoryService.getAnswerHistoryByQuizHistoryAndAnswerId(quizHistory,
                            quiz.getAnswers().get(i).getId());
                    answerHistoryService.delete(answerHistory);
                    quizHistory.getAnswerHistories().remove(answerHistory);
                }
            }
        }
        quizHistoryService.save(quizHistory);
        return "success";
    }




}
