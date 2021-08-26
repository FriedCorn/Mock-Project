package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.service.QuizHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/student/activity")
public class StudentActivityController {
    private final QuizHistoryService quizHistoryService;

    public StudentActivityController(QuizHistoryService quizHistoryService) {
        this.quizHistoryService = quizHistoryService;
    }

    @GetMapping("/running")
    public String getRunning(Model model, Principal principal) {
        List<ListQuiz> quizList = quizHistoryService.getDoingQuizzes(principal.getName());
        model.addAttribute("username", principal.getName());
        model.addAttribute("listQuizList", quizList);
        return "studentRunning";
    }

    @GetMapping("/completed")
    public String getCompleted(Model model, Principal principal) {
        List<ListQuiz> quizList = quizHistoryService.getFinishedQuizzes(principal.getName());
        model.addAttribute("username", principal.getName());
        model.addAttribute("listQuizList", quizList);
        return "studentCompleted";
    }
}
