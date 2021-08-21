package com.mockproject.quizweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class playQuizController {

	@GetMapping(value = { "/playQuiz" })
	public String playQuiz(Model model, String error) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");
		return "playQuiz";
	}
}
