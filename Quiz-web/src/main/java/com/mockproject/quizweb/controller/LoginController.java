package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.domain.Role;
import com.mockproject.quizweb.service.AccountService;

import com.mockproject.quizweb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    private final AccountService accountService;
    private final RoleService roleService;

    @Autowired
    public LoginController(AccountService accountService, RoleService roleService) {
        this.accountService = accountService;
        this.roleService = roleService;
    }

    @GetMapping(value = {"/login"})
    public String login(Model model, String error) {
//        System.out.println(accountService.getCurrentAccount().getUsername());
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        return "login";
    }

    @GetMapping(value = {"/signup"})
    public ModelAndView getSignup(ModelAndView mv, String error) {
        mv.setViewName("signup");
        mv.addObject("account", new Account());
        return mv;
    }

    @PostMapping(value = {"/signup"})
    public ModelAndView postSignup(ModelAndView mv, @ModelAttribute("account") Account account) {
        if (accountService.findAccountByUsername(account.getUsername()) != null) {
            mv.addObject("error", "*** Username is existed ***");
            // mv.setViewName("signup");
            return mv;
        }

        if (accountService.findAccountByEmail(account.getEmail()) != null) {
            mv.addObject("error", "*** Email is exsited ***");
            // mv.setViewName("signup");
            return mv;
        }

        Role role = roleService.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        account.setRoles(roles);
        accountService.save(account);

        mv.clear();
        mv.setViewName("signup");
        mv.addObject("success", "Signup successfully !");
        return mv;
    }
}
