package com.mockproject.quizweb.domain.form;

import lombok.Data;

@Data
public class ListQuizForm {
    private String quizName;
    private String categoryName;
    private String timeLimitHour;
    private String timeLimitMinute;
    private String timeLimitSecond;
    private String imgLstquiz;
}
