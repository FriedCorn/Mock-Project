package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.domain.QuizHistory;
import com.mockproject.quizweb.service.ListQuizService;
import com.mockproject.quizweb.service.QuizHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/report")
public class ViewReportController {
    private final QuizHistoryService quizHistoryService;
    private final ListQuizService listQuizService;

    @Autowired
    public ViewReportController(QuizHistoryService quizHistoryService, ListQuizService listQuizService) {
        this.quizHistoryService = quizHistoryService;
        this.listQuizService = listQuizService;
    }

    @GetMapping("{list_quiz_id}")
    public String getListQuiz(Model model,
                              @PathVariable Integer list_quiz_id,
                              Principal principal) {
        ListQuiz listQuiz = listQuizService.getListQuizById(list_quiz_id);
        model.addAttribute("listQuiz", listQuiz);
        model.addAttribute("username", principal.getName());
        return "instructorReportDetail";
    }

    @GetMapping(value = {"/{list_quiz_id}/get-account-grade"}, produces = "application/json")
    public @ResponseBody Hashtable<String, Object> getQuiz(@PathVariable Integer list_quiz_id) {
        List<QuizHistory> quizHistoryList = quizHistoryService.getQuizHistoriesByListQuiz_Id(list_quiz_id);
        List<Account> accounts = quizHistoryService.getAccountsOfQuizHistoriesByListQuiz_Id(list_quiz_id);
        List<Float> grades = new ArrayList<>();
        List<String> name = new ArrayList<>();
        for (Account account: accounts) {
            List<QuizHistory> quizHistoryListOfAccount =
                    quizHistoryService.getQuizHistoriesByListQuiz_IdAndAccount_Id(list_quiz_id, account.getId());
            float meanGrade = quizHistoryService.calculateMean(quizHistoryListOfAccount) * 100;
            meanGrade = Math.round(meanGrade * 100) / (float) 100;
            grades.add(meanGrade);
            name.add(account.getUsername());
        }
        Hashtable<String, Object> ret = new Hashtable<>();
        ret.put("username", name);
        ret.put("grades", grades);
        return ret;
    }
}
