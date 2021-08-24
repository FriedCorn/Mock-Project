package com.mockproject.quizweb.DTO;

import lombok.Data;

@Data
public class ListQuizDTO {
     private Long   id;
     private String quizName;
     private String categoryName;
     private String timeLimit;
}
