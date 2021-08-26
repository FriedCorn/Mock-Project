package com.mockproject.quizweb.domain.form;

import lombok.Data;

@Data
public class AccountForm {
    private String username;
    private String password;
    private String rePassword;
    private String firstName;
    private String lastName;
    private String doB;
    private String email;
}
