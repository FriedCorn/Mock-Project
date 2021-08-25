package com.mockproject.quizweb.controller;

import java.util.List;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.domain.Category;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.form.ListQuizForm;
import com.mockproject.quizweb.service.AccountService;
import com.mockproject.quizweb.service.CategoryService;
import com.mockproject.quizweb.service.ListQuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InstructorModeController {
    private final ListQuizService listQuizService;
    private final CategoryService categoryService;
    private final AccountService accountService;

    @Autowired
    public InstructorModeController(ListQuizService listQuizService, CategoryService categoryService,
            AccountService accountService) {
        this.listQuizService = listQuizService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @GetMapping(value = { "/listQuiz/instructor", "/instructor-home" })
    public ModelAndView getListQuizForInstructor(ModelAndView mv) {
        Account account = accountService.getCurrentAccount();
        mv.addObject("account", account);
        
        List<Category> categoryList = categoryService.getAllCategory();
        
        mv.addObject("categoryList", categoryList);
        mv.setViewName("instructorHome");
        
        List<ListQuiz> listQuizList = listQuizService.getAllListQuiz();
        mv.addObject("listQuizList", listQuizList);
        
        mv.addObject("listQuizForm", new ListQuizForm());
        
        return mv;
    }

    @GetMapping(value = { "/listQuiz/instructor/library", "/instructor-library" })
    public ModelAndView getListQuizLibrary(ModelAndView mv) {
        Account account = accountService.getCurrentAccount();

        if (account == null) {
            return new ModelAndView("redirect:/login");
        }

        return mv;
    }

}
