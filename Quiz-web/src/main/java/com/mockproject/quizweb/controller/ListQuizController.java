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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping(value = { "/createListQuiz" })
    public ModelAndView getCreateTest(ModelAndView mv, String error) {
        mv.setViewName("createListQuiz");
        mv.addObject("listQuizForm", new ListQuizForm());

        List<Category> categoryList = categoryService.getAllCategory();
        System.out.println(categoryList);

        mv.addObject("categoryList", categoryList);

        return mv;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Account account = accountService.findAccountByUsername(username);

        if (account != null) {
            listQuiz.setAccount(account);
            listQuizService.create(listQuiz);
        }

        return mv;
    }
}
