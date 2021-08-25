package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    
    @PostMapping("/question/delete")
    @ResponseBody
    public String questionDelete(@ModelAttribute("quiz") Quiz quiz) {
        quizService.delete(quiz);
        
        return "OK";
    }

    @PostMapping("/question/update")
    @ResponseBody
    public String questionUpdate(@ModelAttribute("quiz") Quiz quiz) {
        quizService.update(quiz);

        return "OK";
    }
}
