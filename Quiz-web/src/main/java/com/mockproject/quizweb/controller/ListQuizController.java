package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Category;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.domain.form.ListQuizForm;
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

    @Autowired
    public ListQuizController(ListQuizService listQuizService, CategoryService categoryService) {
        this.listQuizService = listQuizService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = { "/createListQuiz" })
    public ModelAndView getCreateTest(ModelAndView mv, String error) {
        mv.setViewName("createListQuiz");
        mv.addObject("listQuizForm", new ListQuizForm());

        Category category;
        List<Category> categoryList = new ArrayList<Category>();

        category = new Category();
        category.setName("Math");
        categoryList.add(category);

        category = new Category();
        category.setName("English");
        categoryList.add(category);

        category = new Category();
        category.setName("Physic");
        categoryList.add(category);

        mv.addObject("categoryList", categoryList);
        
        return mv;
    }

    @PostMapping(value = { "/createListQuiz" })
    public ModelAndView setCreateTest(ModelAndView mv, @ModelAttribute("listQuizForm") ListQuizForm listQuizForm) {
        // ListQuiz listQuiz = new ListQuiz();
        // Category category = categoryService.findByName(listQuizForm.getCategoryName()).orElse(null);
        // List<Quiz> quizList = new ArrayList<>();
        // listQuiz.setName(listQuizForm.getQuizName());
        // listQuiz.setNumberOfQuiz(0);
        // listQuiz.setTimeLimit(listQuizForm.getTimeLimit());
        // listQuiz.setCategory(category);
        // listQuiz.setQuizzes(quizList);
        // listQuizService.create(listQuiz);

        String timeLimit = listQuizForm.getTimeLimitHour() + ":" + listQuizForm.getTimeLimitMinute() + ":" + listQuizForm.getTimeLimitSecond();

        System.out.println(listQuizForm.getQuizName());
        System.out.println(listQuizForm.getCategoryName());
        System.out.println(timeLimit);
        return mv;
    }
}
