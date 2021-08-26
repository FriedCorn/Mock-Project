package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Answer;
import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.domain.Quiz;
import com.mockproject.quizweb.domain.form.QuizForm;
import com.mockproject.quizweb.service.ListQuizService;
import com.mockproject.quizweb.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizController {
    private final QuizService quizService;
    private final ListQuizService listQuizService;

    @Autowired
    public QuizController(QuizService quizService, ListQuizService listQuizService) {
        this.quizService = quizService;
        this.listQuizService = listQuizService;
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

    @PostMapping("/quiz/add/{list_quiz_id}")
    public String questionAdd(@ModelAttribute("question")QuizForm quizForm,
                              @PathVariable Integer list_quiz_id){
        List<Answer> answerList=new ArrayList<>(4);
        ListQuiz listQuizById = listQuizService.getListQuizById(list_quiz_id);
        Quiz quiz = new Quiz();
        quiz.setContent(quizForm.getQuestion());
        quiz.setCorrectedAnswer(true);
        quiz.setImgSrc("");
        for (int i=0;i<answerList.size();i++){
            answerList.set(i,new Answer(69,quizForm.getChoice()[i],"",quiz,false));
        }
        if(quizForm.getAnswer().contains("A")){
            answerList.get(0).setTrueAnswer(true);
        }
        if(quizForm.getAnswer().contains("B")){
            answerList.get(1).setTrueAnswer(true);
        }
        if(quizForm.getAnswer().contains("C")){
            answerList.get(2).setTrueAnswer(true);
        }
        if(quizForm.getAnswer().contains("D")){
            answerList.get(3).setTrueAnswer(true);
        }
        quiz.setAnswers(answerList);
        quiz.setListQuiz(listQuizById);
        int current = listQuizById.getNumberOfQuiz();
        listQuizById.setNumberOfQuiz(current+1);
        return "";
        

    }

}
