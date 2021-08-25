package com.mockproject.quizweb.domain.form;

import lombok.Data;

@Data
public class QuizForm {
     private String question;
     private String[] choice = new String[4];
     private String answer;
}
