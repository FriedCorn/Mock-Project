package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.domain.Category;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.domain.form.ListQuizForm;
import com.mockproject.quizweb.service.AccountService;
import com.mockproject.quizweb.service.CategoryService;
import com.mockproject.quizweb.service.ListQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ListQuizController {
    private final ListQuizService listQuizService;
    private final CategoryService categoryService;
    private final AccountService accountService;

    @Autowired
    public ListQuizController(ListQuizService listQuizService, CategoryService categoryService,
            AccountService accountService) {
        this.listQuizService = listQuizService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @PostMapping(value = { "/createListQuiz" })
    public ModelAndView setCreateTest(ModelAndView mv, @ModelAttribute("listQuizForm") ListQuizForm listQuizForm) {
        String timeLimit = listQuizForm.getTimeLimitHour() + ":" + listQuizForm.getTimeLimitMinute() + ":"
                + listQuizForm.getTimeLimitSecond();

        ListQuiz listQuiz = new ListQuiz();
        Category category = categoryService.findByName(listQuizForm.getCategoryName()).orElse(null);
        List<Quiz> quizList = new ArrayList<>();
        listQuiz.setName(listQuizForm.getQuizName());
        listQuiz.setNumberOfQuiz(0);
        listQuiz.setTimeLimit(timeLimit);
        listQuiz.setCategory(category);
        listQuiz.setQuizzes(quizList);

        // Get account info
        Account account = accountService.getCurrentAccount();

        if (account != null) {
            listQuiz.setAccount(account);
            listQuizService.create(listQuiz);
        }

        return new ModelAndView("redirect:/quiz");
    }
}
