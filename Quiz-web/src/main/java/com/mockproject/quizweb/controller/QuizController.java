package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    
    @PostMapping("/quiz/delete")
    @ResponseBody
    public String questionDelete(@ModelAttribute("quiz") Quiz quiz) {
        quizService.delete(quiz);
        
        return "OK";
    }

    @PostMapping("/quiz/update")
    @ResponseBody
    public String questionUpdate(@ModelAttribute("quiz") Quiz quiz) {
        quizService.update(quiz);

        return "OK";
    }

    @GetMapping("/quiz")
    public ModelAndView getMethodName(ModelAndView mv) {
        return mv;
    }
    
}
