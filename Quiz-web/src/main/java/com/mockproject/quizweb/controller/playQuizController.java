package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.ListQuiz;
import com.mockproject.quizweb.service.ListQuizService;
import com.mockproject.quizweb.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
class playQuizController {


	@GetMapping(value = { "/playQuiz" })
	public String playQuiz(Model model, String error) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");
		return "playQuiz";
	}





}
