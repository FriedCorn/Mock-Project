package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.domain.QuizHistory;
import com.mockproject.quizweb.service.ListQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Hashtable;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class ViewQuizController {
    private final ListQuizService listQuizService;

    @Autowired
    public ViewQuizController(ListQuizService listQuizService) {
        this.listQuizService = listQuizService;
    }

    @GetMapping("{list_quiz_id}")
    public String getListQuiz(Model model,
                              @PathVariable Integer list_quiz_id,
                              Principal principal) {
        ListQuiz listQuiz = listQuizService.getListQuizById(list_quiz_id);
        model.addAttribute("listQuiz", listQuiz);
        model.addAttribute("username", principal.getName());
        return "instructorViewQuiz";
    }

    @GetMapping(value = {"/{list_quiz_id}/get-all-quiz"}, produces = "application/json")
    public @ResponseBody List<Quiz>
    getQuiz(@PathVariable Integer list_quiz_id) {
        ListQuiz listQuiz = listQuizService.getListQuizById(list_quiz_id);
        return listQuiz.getQuizzes();
    }
}
