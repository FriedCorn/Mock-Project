package com.mockproject.quizweb.controller;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.domain.ListRole;
import com.mockproject.quizweb.domain.Role;
import com.mockproject.quizweb.service.AccountService;
import com.mockproject.quizweb.service.ListRoleService;
import com.mockproject.quizweb.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    private final AccountService accountService;
    private final ListRoleService listRoleService;
    private final RoleService roleService;

    @Autowired
    public LoginController(AccountService accountService, ListRoleService listRoleRepository, RoleService roleRepository) {
        this.accountService = accountService;
        this.listRoleService = listRoleRepository;
        this.roleService = roleRepository;
    }

    @GetMapping(value = {"/", "/login"})
    public String login(Model model, String error) {
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
            mv.setViewName("signup");
            mv.addObject("error", "*** Username is existed ***");
            return mv;
        }

        
        accountService.save(account);

        Role role = roleService.findByName("ROLE_USER");
        
        ListRole listRole = new ListRole();
        listRole.setAccount(accountService.findAccountByUsername(account.getUsername()));
        listRole.setRole(role);
        listRoleService.save(listRole);

        mv.setViewName("signup");
        mv.addObject("success", "Signup successfully !");
        return mv;
    }
}
