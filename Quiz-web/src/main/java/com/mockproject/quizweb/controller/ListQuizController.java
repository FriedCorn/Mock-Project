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
        mv.setViewName("createTest");
        mv.addObject("test", new ListQuiz());
        return mv;
    }

    // ListQuizDTO để mang object của giao diện về convert qua object trong DB
    @PostMapping(value = { "/createListQuiz" })
    public ModelAndView setCreateTest(ModelAndView mv, @ModelAttribute("quizForm") ListQuizForm listQuizForm) {
        ListQuiz listQuiz = new ListQuiz();
        Category category = categoryService.findByName(listQuizForm.getCategoryName()).orElse(null);
        List<Quiz> quizList = new ArrayList<>();
        listQuiz.setName(listQuizForm.getQuizName());
        listQuiz.setNumberOfQuiz(0);
        listQuiz.setTimeLimit(listQuizForm.getTimeLimit());
        listQuiz.setCategory(category);
        listQuiz.setQuizzes(quizList);
        listQuizService.create(listQuiz);
        return mv;
    }
}
