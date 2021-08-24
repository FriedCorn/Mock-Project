package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.DTO.ListQuizDTO;
import com.mockproject.quizweb.domain.Category;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.service.CategoryService;
import com.mockproject.quizweb.service.ListQuizService;
import com.mockproject.quizweb.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizManagerController {
     private final QuizService quizService;
     private final ListQuizService listQuizService;
     private final CategoryService categoryService;

     @Autowired
     public QuizManagerController(QuizService quizService, ListQuizService listQuizService, CategoryService categoryService) {
          this.quizService = quizService;
          this.listQuizService = listQuizService;
          this.categoryService = categoryService;
     }


     @GetMapping(value = {"/createTest"})
     public ModelAndView getCreateTest(ModelAndView mv, String error) {
          mv.setViewName("createTest");
          mv.addObject("test", new ListQuiz());
          return mv;
     }

     //ListQuizDTO để mang object của giao diện về convert qua object trong DB
     @PostMapping(value = {"/createTest"})
     public ModelAndView setCreateTest(ModelAndView mv, @ModelAttribute("test") ListQuizDTO listQuizDTO){
          ListQuiz listQuiz = new ListQuiz();
          Category category = categoryService.findByName(listQuizDTO.getCategoryName()).orElse(null);
          List<Quiz> quizList = new ArrayList<>();
          listQuiz.setId(listQuizDTO.getId());
          listQuiz.setName(listQuizDTO.getQuizName());
          listQuiz.setNumberOfQuiz(0);
          listQuiz.setTimeLimit(listQuizDTO.getTimeLimit());
          listQuiz.setCategory(category);
          listQuiz.setQuizzes(quizList);
          listQuizService.create(listQuiz);
          return mv;
     }
}
