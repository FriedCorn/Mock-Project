package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Category;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.service.CategoryService;
import com.mockproject.quizweb.service.ListQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final ListQuizService listQuizService;
    private final CategoryService categoryService;

    @Autowired
    public HomeController(ListQuizService listQuizService, CategoryService categoryService) {
        this.listQuizService = listQuizService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = { "/" })
    public String getListQuizForStudent(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
        }
        List<Category> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        List<ListQuiz> listQuizList = listQuizService.getAllListQuiz();
        model.addAttribute("listQuizList", listQuizList);
        return "home";
    }
}
