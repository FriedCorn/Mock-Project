package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Answer;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.domain.form.QuizForm;
import com.mockproject.quizweb.repository.AnswerReposity;
import com.mockproject.quizweb.service.ListQuizService;
import com.mockproject.quizweb.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizController {
    private final QuizService quizService;
    private final ListQuizService listQuizService;
    private final AnswerReposity answerReposity;

    @Autowired
    public QuizController(QuizService quizService, ListQuizService listQuizService, AnswerReposity answerReposity) {
        this.quizService = quizService;
        this.listQuizService = listQuizService;
        this.answerReposity = answerReposity;
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

    @PostMapping("/createQuiz/{list_quiz_id}")
    public ModelAndView questionAdd(@ModelAttribute("quizForm") QuizForm quizForm, @PathVariable Integer list_quiz_id) {
        List<Answer> answerList = new ArrayList<Answer>();
        ListQuiz listQuizById = listQuizService.getListQuizById(list_quiz_id);
        Quiz quiz = new Quiz();
        quiz.setContent(quizForm.getQuestion());
        quiz.setCorrectedAnswer(true);
        quiz.setImgSrc("");
        for (int i = 0; i < 4; i++) {
            answerList.add(new Answer(0, quizForm.getChoice()[i], "", quiz, false));
        }
        if (quizForm.getAnswer().contains("A")) {
            answerList.get(0).setTrueAnswer(true);
        }
        if (quizForm.getAnswer().contains("B")) {
            answerList.get(1).setTrueAnswer(true);
        }
        if (quizForm.getAnswer().contains("C")) {
            answerList.get(2).setTrueAnswer(true);
        }
        if (quizForm.getAnswer().contains("D")) {
            answerList.get(3).setTrueAnswer(true);
        }
        quiz.setAnswers(answerList);
        quiz.setListQuiz(listQuizById);
        int current = listQuizById.getNumberOfQuiz();
        listQuizById.setNumberOfQuiz(current + 1);
        quizService.create(quiz);
        listQuizService.create(listQuizById);
        for (int i = 0; i < 4; i++)
            answerReposity.save(answerList.get(i));

        return new ModelAndView("redirect:/quiz/" + list_quiz_id);

    }

}
