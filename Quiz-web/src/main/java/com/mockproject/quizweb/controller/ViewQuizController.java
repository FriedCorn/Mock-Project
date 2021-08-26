package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.service.ListQuizService;
import com.mockproject.quizweb.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class ViewQuizController {
    private final ListQuizService listQuizService;
    private final QuizService quizService;

    @Autowired
    public ViewQuizController(ListQuizService listQuizService, QuizService quizService) {
        this.listQuizService = listQuizService;
        this.quizService = quizService;
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

    @GetMapping(value = "/{list_quiz_id}/delete/{quiz_id}")
    public  @ResponseBody List<Quiz> deleteQuiz(@PathVariable Integer list_quiz_id,
                                                @PathVariable Integer quiz_id) {
        ListQuiz listQuiz = listQuizService.getListQuizById(list_quiz_id);
        Quiz quiz = quizService.getQuizById(quiz_id);
        listQuiz.getQuizzes().remove(quiz);
        listQuiz.setNumberOfQuiz(listQuiz.getNumberOfQuiz() - 1);
        quiz.setListQuiz(null);
        quizService.delete(quiz);
        return listQuiz.getQuizzes();
    }
}
