package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final AccountService accountService;

    @Autowired
    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = {"/"})
    public String login(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        return "login";
    }

    @GetMapping(value = {"/signup"})
    public String getSignup(Model model, String error) {
        return "signup";
    }

    @PostMapping(value = {"/signup"})
    public String postSignup(@RequestParam("username") String userName,
                             @RequestParam("password") String password,
                             @RequestParam("repassword") String rePassword,
                             Model model) {
        if (!password.equals(rePassword)) {
            model.addAttribute("error", "Password not match!");
            return "signup";
        }
        if (accountService.findAccountByUsername(userName) != null) {
            model.addAttribute("error", "Username is existed!");
            return "signup";
        }
        Account user = new Account();
        user.setUsername(userName);
        user.setPassword(password);
//        user.setRole("USER");
//        accountService.save(user);
        return "login";
    }
}
