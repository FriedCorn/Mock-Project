package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.domain.Category;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.service.AccountService;
import com.mockproject.quizweb.service.CategoryService;
import com.mockproject.quizweb.service.ListQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final ListQuizService listQuizService;
    private final CategoryService categoryService;
    private final AccountService accountService;

    @Autowired
    public SearchController(ListQuizService listQuizService, CategoryService categoryService,
                            AccountService accountService) {
        this.listQuizService = listQuizService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @GetMapping(value = { ""})
    public ModelAndView getListQuizForStudent(ModelAndView mv,
                                              @RequestParam(required = false) Integer list_id) {
        Account account = accountService.getCurrentAccount();
        mv.addObject("account", account);

        List<Category> categoryList = categoryService.getAllCategory();

        mv.addObject("categoryList", categoryList);
        mv.setViewName("studentHome");

        List<ListQuiz> listQuizList = (list_id != null)?listQuizService.getListQuizsById(list_id):null;
        mv.addObject("listQuizList", listQuizList);

        return mv;
    }
}
